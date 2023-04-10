package com.nisum.api.service;

import com.nisum.api.dto.response.UserResponseDTO;
import com.nisum.api.entity.UserEntity;
import com.nisum.api.mapper.UserMapper;
import com.nisum.api.model.Phone;
import com.nisum.api.model.User;
import com.nisum.api.repository.PhoneRepository;
import com.nisum.api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Servicio encargado de la lógica de negocio relacionada con los usuarios.
 */
@Service
@Transactional
public class UserService {
  // el email debe tener el formato (aaaaaaa@dominio.cl)
  public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

  // la contraseña debe tener de 8 a 16 caracteres, números, letras minúsculas y mayúsculas (123Acb1234*)
  public static final String PWD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}$";

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PhoneRepository phoneRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  /**
   * Obtiene una lista de todos los usuarios.
   *
   * @return Lista de usuarios.
   */
  public List<User> getUsers() {
    List<UserEntity> userData = userRepository.findAll();
    return userData.stream().map(UserMapper::toUserModel).collect(Collectors.toList());
  }

  /**
   * Crea un nuevo usuario.
   *
   * @param user Usuario a crear.
   * @return UserResponseDTO DTO de respuesta con información del usuario creado.
   */
  public UserResponseDTO createUser(User user) {
    // Valida que el email y la contraseña cumplan con los formatos requeridos
    validateEmail(user.getEmail());
    validatePassword(user.getPassword());

    // Configura los datos iniciales del usuario
    user.setId(UUID.randomUUID().toString());
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setCreated(LocalDate.now());
    user.setLastLogin(user.getLastLogin() != null ? user.getLastLogin() : user.getCreated());
    user.setToken(UUID.randomUUID().toString());

    // Guarda el usuario y sus teléfonos correspondientes en la base de datos
    User getUser = UserMapper.toUserModel(userRepository.save(UserMapper.toUserEntity(user)));
    if (getUser != null) {
      getUser.setPhones(user.getPhones());
      user.getPhones().forEach(p -> {
        Phone phones = new Phone();
        phones.setNumber(p.getNumber());
        phones.setCitycode(p.getCitycode());
        phones.setCountrycode(p.getCountrycode());
        phones.setUserId(getUser.getId());
        phoneRepository.save(UserMapper.toPhoneEntity(phones));
      });
    }

    // Arma y devuelve la respuesta
    return setResponse(user);
  }

  /**
   * Actualiza los datos de un usuario existente.
   *
   * @param user Usuario a actualizar.
   * @return UserResponseDTO DTO de respuesta con información del usuario actualizado.
   */
  public UserResponseDTO updateUser(User user) {
    // Busca si existe un usuario con el email dado
    Optional<UserEntity> found = userRepository.findByEmail(user.getEmail());
    if (found.isEmpty()) {
      throw new IllegalArgumentException();
    }

    // Actualiza los datos del usuario
    user.setId(found.get().getId());
    user.setName(user.getName());
    user.setEmail(user.getEmail());
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setPhones(user.getPhones());
    user.setCreated(found.get().getCreated());
    user.setModified(LocalDate.now());
    user.setLastLogin(found.get().getLastLogin());
    user.setToken(found.get().getToken());

    // Guarda los cambios del usuario en la base de datos y guarda los teléfonos del usuario
    User getUser = UserMapper.toUserModel(userRepository.save(UserMapper.toUserEntity(user)));
    if (getUser != null) {
      getUser.setPhones(user.getPhones());
      user.getPhones().forEach(p -> {
        Phone phones = new Phone();
        phones.setNumber(p.getNumber());
        phones.setCitycode(p.getCitycode());
        phones.setCountrycode(p.getCountrycode());
        phones.setUserId(getUser.getId());
        phoneRepository.save(UserMapper.toPhoneEntity(phones));
      });
    }

    // Arma y devuelve la información actualizada del usuario
    return setResponse(user);
  }

  /**
   * Valida que el email tenga el formato correcto y no esté registrado previamente.
   *
   * @param email correo electrónico a validar
   * @throws ResponseStatusException si el email tiene un formato incorrecto o ya está registrado
   */
  private void validateEmail(String email) {
    Pattern pattern = Pattern.compile(EMAIL_REGEX);
    Matcher matcher = pattern.matcher(email);
    if (!matcher.matches()) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "El correo tiene un formato incorrecto"
      );
    }
    if (userRepository.findByEmail(email).isPresent()) {
      throw new ResponseStatusException(
          HttpStatus.CONFLICT,
          "El correo " + email + " ya está registrado"
      );
    }
  }

  /**
   * Valida que la contraseña tenga el formato correcto.
   *
   * @param password contraseña a validar
   * @throws ResponseStatusException si la contraseña tiene un formato incorrecto
   */
  private void validatePassword(String password) {
    Pattern pattern = Pattern.compile(PWD_REGEX);
    Matcher matcher = pattern.matcher(password);
    if (!matcher.matches()) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "La contraseña tiene un formato incorrecto"
      );
    }
  }

  /**
   * Crea una respuesta de usuario a partir del modelo de usuario.
   *
   * @param user modelo de usuario
   * @return UserResponseDTO DTO de respuesta de usuario
   */
  private UserResponseDTO setResponse(User user) {
    return UserResponseDTO.builder()
        .id(UUID.fromString(user.getId()))
        .name(user.getName())
        .email(user.getEmail())
        .password(user.getPassword())
        .phones(user.getPhones())
        .created(user.getCreated())
        .modified(user.getModified())
        .lastLogin(user.getLastLogin() != null ? user.getLastLogin() : user.getCreated())
        .token(user.getToken())
        .isActive(user.getIsActive() == null ? Boolean.TRUE : Boolean.FALSE)
        .build();
  }
}

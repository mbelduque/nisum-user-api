package com.nisum.api.service;

import com.nisum.api.dto.response.UserResponseDTO;
import com.nisum.api.entity.UserEntity;
import com.nisum.api.mapper.UserMapper;
import com.nisum.api.model.Phone;
import com.nisum.api.model.User;
import com.nisum.api.repository.PhoneRepository;
import com.nisum.api.repository.UserRepository;
import com.nisum.api.util.Util;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Servicio encargado de la lógica de negocio relacionada con los usuarios.
 */
@Service
@Transactional
public class UserService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PhoneRepository phoneRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private Util util;

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
    util.validateEmail(user.getEmail());
    util.validatePassword(user.getPassword());

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
    return util.setResponse(user);
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
    return util.setResponse(user);
  }
}

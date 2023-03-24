package com.nisum.api.util;

import com.nisum.api.dto.response.UserResponseDTO;
import com.nisum.api.model.User;
import com.nisum.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
  @Autowired
  private UserRepository userRepository;

  // el email debe tener el formato (aaaaaaa@dominio.cl)
  public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

  // la contraseña debe tener de 8 a 16 caracteres, números, letras minúsculas y mayúsculas (123Acb1234*)
  public static final String PWD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}$";

  public void validateEmail(String email) {
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

  public void validatePassword(String password) {
    Pattern pattern = Pattern.compile(PWD_REGEX);
    Matcher matcher = pattern.matcher(password);
    if (!matcher.matches()) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "La contraseña tiene un formato incorrecto"
      );
    }
  }

  public UserResponseDTO setResponse(User user) {
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

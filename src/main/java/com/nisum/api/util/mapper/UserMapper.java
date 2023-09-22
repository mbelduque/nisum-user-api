package com.nisum.api.util.mapper;

import com.nisum.api.entity.PhoneEntity;
import com.nisum.api.entity.UserEntity;
import com.nisum.api.model.Phone;
import com.nisum.api.model.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Clase para mapear entre los modelos y entidades de usuario y teléfono.
 */
@Component
public class UserMapper {

  /**
   * Convierte una entidad de usuario en un modelo de usuario.
   *
   * @param userEntity La entidad de usuario a convertir.
   * @return User modelo de usuario correspondiente.
   */
  public static User toUserModel(UserEntity userEntity) {
    return User.builder()
        .id(userEntity.getId())
        .name(userEntity.getName())
        .email(userEntity.getEmail())
        .password(userEntity.getPassword())
        .phones(userEntity.getPhones().stream().map(phoneEntity ->
                Phone.builder()
                    .number(phoneEntity.getNumber())
                    .citycode(phoneEntity.getCityCode())
                    .countrycode(phoneEntity.getCountryCode())
                    .build())
            .collect(Collectors.toList()))
        .created(userEntity.getCreated())
        .modified(userEntity.getModified())
        .lastLogin(userEntity.getLastLogin())
        .isActive(userEntity.getIsActive() == null ? Boolean.FALSE : Boolean.TRUE)
        .token(userEntity.getToken())
        .build();
  }

  /**
   * Convierte un modelo de usuario en una entidad de usuario.
   *
   * @param user El modelo de usuario a convertir.
   * @return UserEntity entidad de usuario correspondiente.
   */
  public static UserEntity toUserEntity(User user) {
    UserEntity userEntity = new UserEntity();
    userEntity.setId(user.getId());
    userEntity.setName(user.getName());
    userEntity.setEmail(user.getEmail());
    userEntity.setPassword(user.getPassword());
    userEntity.setCreated(user.getCreated());
    userEntity.setModified(user.getModified());
    userEntity.setLastLogin(user.getLastLogin());
    userEntity.setToken(user.getToken());
    userEntity.setIsActive(user.getIsActive() == null ? Boolean.TRUE : Boolean.FALSE);
    return userEntity;
  }

  /**
   * Convierte un modelo de teléfono en una entidad de teléfono.
   *
   * @param phone El modelo de teléfono a convertir.
   * @return PhoneEntity entidad de teléfono correspondiente.
   */
  public static PhoneEntity toPhoneEntity(Phone phone) {
    PhoneEntity phoneEntity = new PhoneEntity();
    phoneEntity.setNumber(phone.getNumber());
    phoneEntity.setCityCode(phone.getCitycode());
    phoneEntity.setCountryCode(phone.getCountrycode());
    UserEntity userEntity = new UserEntity();
    userEntity.setId(phone.getUserId());
    phoneEntity.setUser(userEntity);
    return phoneEntity;
  }
}

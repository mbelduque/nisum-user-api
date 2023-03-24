package com.nisum.api.mapper;

import com.nisum.api.entity.PhoneEntity;
import com.nisum.api.entity.UserEntity;
import com.nisum.api.model.Phone;
import com.nisum.api.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
  public static User toDto(UserEntity userEntity) {
    return User.builder()
        .id(userEntity.getId())
        .name(userEntity.getName())
        .email(userEntity.getEmail())
        .password(userEntity.getPassword())
        .created(userEntity.getCreated())
        .modified(userEntity.getModified())
        .lastLogin(userEntity.getLastLogin())
        .isActive(userEntity.getIsActive() == null ? Boolean.TRUE : Boolean.FALSE)
        .token(userEntity.getToken())
        .build();
  }

  public static UserEntity toEntity(User user) {
    UserEntity userEntity = new UserEntity();
    userEntity.setId(user.getId());
    userEntity.setName(user.getName());
    userEntity.setEmail(user.getEmail());
    userEntity.setPassword(user.getPassword());
    userEntity.setCreated(user.getCreated());
    userEntity.setModified(user.getModified());
    userEntity.setLastLogin(user.getLastLogin() != null ? user.getLastLogin() : user.getCreated());
    userEntity.setToken(user.getToken());
    userEntity.setIsActive(user.getIsActive() == null ? Boolean.TRUE : Boolean.FALSE);
    return userEntity;
  }

  public static PhoneEntity toModelPhoneDTO(Phone phone) {
    PhoneEntity phoneEntity = new PhoneEntity();
    phoneEntity.setNumber(phone.getNumber());
    phoneEntity.setCityCode(phone.getCitycode());
    phoneEntity.setCountryCode(phone.getCountrycode());
    UserEntity userEntity = new UserEntity();
    userEntity.setId(phone.getUserId());
    phoneEntity.setUserEntity(userEntity);
    return phoneEntity;
  }
}

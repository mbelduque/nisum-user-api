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

  public List<User> getUsers() {
    List<UserEntity> userData = userRepository.findAll();
    return userData.stream().map(UserMapper::toUserModel).collect(Collectors.toList());
  }

  public UserResponseDTO createUser(User user) {
    util.validateEmail(user.getEmail());
    util.validatePassword(user.getPassword());

    user.setId(UUID.randomUUID().toString());
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setCreated(LocalDate.now());
    user.setLastLogin(user.getLastLogin() != null ? user.getLastLogin() : user.getCreated());
    user.setToken(UUID.randomUUID().toString());

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

    return util.setResponse(user);
  }

  public UserResponseDTO updateUser(User user) {
    Optional<UserEntity> found = userRepository.findByEmail(user.getEmail());
    if (found.isEmpty()) {
      throw new IllegalArgumentException();
    }

    user.setId(found.get().getId());
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setCreated(found.get().getCreated());
    user.setModified(LocalDate.now());
    user.setLastLogin(found.get().getLastLogin());
    user.setToken(UUID.randomUUID().toString());

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

    return util.setResponse(user);
  }
}

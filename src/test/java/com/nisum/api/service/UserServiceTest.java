package com.nisum.api.service;

import com.nisum.api.dto.response.UserResponseDTO;
import com.nisum.api.entity.PhoneEntity;
import com.nisum.api.entity.UserEntity;
import com.nisum.api.model.Phone;
import com.nisum.api.model.User;
import com.nisum.api.repository.PhoneRepository;
import com.nisum.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
  @Mock
  private UserRepository mockedUserRepository;

  @Mock
  private PhoneRepository mockedPhoneRepository;

  @Mock
  private PasswordEncoder mockedPasswordEncoder;

  @Mock
  private User mockedUser;

  @Mock
  private Phone mockedPhone;

  @Mock
  private UserEntity mockedUserEntity;

  @InjectMocks
  private UserService userService;

  @BeforeEach
  void setUp() {
    mockedUser = new User();
    mockedUser.setId("788589f5-fc9b-4cbe-8535-328bdc723456");
    mockedUser.setName("Test User");
    mockedUser.setEmail("test@nisum.com");
    mockedUser.setPassword("123Acb144*");
    mockedUser.setPhones(new ArrayList<>());
    mockedUser.getPhones().add(mockedPhone);
  }

  @Test
  void getUsers_returnsListOfUsers() {
    //given
    List<UserEntity> userList = new ArrayList<>();
    userList.add(mockedUserEntity);
    when(mockedUserRepository.findAll()).thenReturn(userList);
    //when
    List<User> users = userService.getUsers();
    //then
    assertEquals(users.size(), 1);
  }

  @Test
  void createUser_returnsUserResponseDTO() {
    // Mockear dependencias externas
    when(mockedPasswordEncoder.encode(anyString())).thenReturn("encodedPassword");
    when(mockedUserRepository.save(any(UserEntity.class))).thenReturn(new UserEntity());
    when(mockedPhoneRepository.save(any(PhoneEntity.class))).thenReturn(new PhoneEntity());

    // Ejecutar método a probar
    UserResponseDTO result = userService.createUser(mockedUser);

    // Verificar resultado
    assertNotNull(result);
    assertNotNull(result.getId());
    assertNotNull(result.getToken());
    assertEquals(result.getName(), mockedUser.getName());
    assertEquals(result.getEmail(), mockedUser.getEmail());
    assertEquals(result.getPhones().size(), mockedUser.getPhones().size());
    verify(mockedUserRepository, times(1)).save(any(UserEntity.class));
    verify(mockedPhoneRepository, times(1)).save(any(PhoneEntity.class));
  }
}
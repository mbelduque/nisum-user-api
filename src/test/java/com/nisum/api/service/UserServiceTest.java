package com.nisum.api.service;

import com.nisum.api.dto.response.UserResponseDTO;
import com.nisum.api.entity.PhoneEntity;
import com.nisum.api.entity.UserEntity;
import com.nisum.api.model.Phone;
import com.nisum.api.model.User;
import com.nisum.api.repository.PhoneRespository;
import com.nisum.api.repository.UserRepository;
import com.nisum.api.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
  @Mock
  private UserRepository userRepositoryMock;

  @Mock
  private PhoneRespository phoneRespositoryMock;

  @Mock
  private PasswordEncoder passwordEncoderMock;

  @Mock
  private Util utilMock;

  @Mock
  private User userMock;

  @Mock
  private Phone phoneMock;

  @Mock
  private UserEntity userEntityMock;

  @InjectMocks
  private UserService userService;

  @BeforeEach
  void setUp() {
    userMock = new User();
    userMock.setId("788589f5-fc9b-4cbe-8535-328bdc723456");
    userMock.setName("Test User");
    userMock.setEmail("test@nisum.com");
    userMock.setPassword("Password123");
    userMock.setPhones(new ArrayList<>());
    userMock.getPhones().add(phoneMock);
  }

  @Test
  void getUsers_returnsListOfUsers() {
    //given
    List<UserEntity> userList = new ArrayList<>();
    userList.add(userEntityMock);
    //when
    when(userRepositoryMock.findAll()).thenReturn(userList);
    List<User> users = userService.getUsers();
    //then
    assertEquals(users.size(), 1);
  }

  @Test
  void createUser_returnsUserResponseDTO() {
    // Mockear dependencias externas
    doNothing().when(utilMock).validateEmail(anyString());
    doNothing().when(utilMock).validatePassword(anyString());

    when(userRepositoryMock.save(any(UserEntity.class))).thenReturn(new UserEntity());
    when(phoneRespositoryMock.save(any(PhoneEntity.class))).thenReturn(new PhoneEntity());

    // Mockear objeto UserResponseDTO que devuelve el método createUser()
    UserResponseDTO mockedUserResponseDTO = new UserResponseDTO();
    mockedUserResponseDTO.setId(UUID.fromString(UUID.randomUUID().toString()));
    mockedUserResponseDTO.setToken(UUID.randomUUID().toString());

    // Ejecutar método a probar
    when(userService.createUser(userMock)).thenReturn(mockedUserResponseDTO);
    UserResponseDTO result = userService.createUser(userMock);

    // Verificar resultado
    assertNotNull(result);
    assertNotNull(result.getId());
    assertNotNull(result.getToken());
    verify(userRepositoryMock, times(2)).save(any(UserEntity.class));
    verify(phoneRespositoryMock, times(2)).save(any(PhoneEntity.class));
  }
}
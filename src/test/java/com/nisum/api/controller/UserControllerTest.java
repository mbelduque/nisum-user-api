package com.nisum.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.api.dto.request.UserRequestDTO;
import com.nisum.api.dto.response.UserResponseDTO;
import com.nisum.api.model.Phone;
import com.nisum.api.model.User;
import com.nisum.api.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class UserControllerTest {

  @Mock
  private UserService userService;

  @InjectMocks
  private UserController userController;

  private MockMvc mockMvc;

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
  }

  @Test
  public void getUsers_shouldReturnOk() throws Exception {
    // Mockear la respuesta del UserService
    List<UserResponseDTO> users = Arrays.asList(new UserResponseDTO(), new UserResponseDTO());
    when(userService.getUsers()).thenReturn(users);

    // Realizar petición GET a la URL /api/user
    mockMvc.perform(get("/api/users"))
        .andExpect(status().isOk())
        .andExpect(content().json(asJsonString(users), true))
        .andExpect(result -> assertEquals(200, result.getResponse().getStatus()));

    // Verificar que el método getUsers() del UserService fue llamado una vez
    verify(userService, times(1)).getUsers();
  }

  @Test
  void createUser_shouldReturnCreated() throws Exception {
    // Mockear la respuesta del servicio
    UserResponseDTO userResponseDTO = new UserResponseDTO();
    userResponseDTO.setId(UUID.randomUUID());
    userResponseDTO.setToken(UUID.randomUUID().toString());
    when(userService.createUser(any(User.class))).thenReturn(userResponseDTO);

    // Crear el objeto UserRequestDTO para la petición
    UserRequestDTO userRequestDTO = new UserRequestDTO();
    userRequestDTO.setName("Test User");
    userRequestDTO.setEmail("test@nisum.com");
    userRequestDTO.setPassword("Password123");
    List<Phone> phones = new ArrayList<>();
    phones.add(new Phone("1234567890", "1", "234", "123444553464"));
    userRequestDTO.setPhones(phones);

    // Realizar la petición POST
    mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(userRequestDTO)))
        .andExpect(status().isCreated())
        .andExpect(result -> assertEquals(201, result.getResponse().getStatus()));

    // Verificar que se llamó al servicio con el objeto User correspondiente
    ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
    verify(userService, times(1)).createUser(userCaptor.capture());
    User capturedUser = userCaptor.getValue();
    assertNotNull(userRequestDTO.getPhones());
    assertEquals(userRequestDTO.getName(), capturedUser.getName());
    assertEquals(userRequestDTO.getEmail(), capturedUser.getEmail());
    assertEquals(userRequestDTO.getPassword(), capturedUser.getPassword());
    assertEquals(userRequestDTO.getPhones(), capturedUser.getPhones());
  }

  // Utilidad para convertir objetos a JSON
  private static String asJsonString(final Object obj) {
    try {
      final ObjectMapper mapper = new ObjectMapper();
      return mapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
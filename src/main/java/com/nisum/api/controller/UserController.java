package com.nisum.api.controller;

import com.nisum.api.dto.request.UserRequestDTO;
import com.nisum.api.dto.response.UserResponseDTO;
import com.nisum.api.model.User;
import com.nisum.api.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para manejar las solicitudes HTTP relacionadas con usuarios.
 */
@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
  @Autowired
  private UserService userService;

  /**
   * Solicitud HTTP GET para obtener todos los usuarios.
   *
   * @return una lista de objetos User en el cuerpo de la respuesta HTTP
   */
  @GetMapping
  public ResponseEntity<List<User>> getUsers() {
    return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
  }

  /**
   * Solicitud HTTP POST para crear un nuevo usuario.
   *
   * @param userRequestDTO contiene la información del usuario a crear
   * @return UserResponseDTO creado en el cuerpo de la respuesta HTTP
   */
  @PostMapping
  public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
    return new ResponseEntity<>(userService.createUser(userRequestDTO.toModel()), HttpStatus.CREATED);
  }

  /**
   * Solicitud HTTP PUT para actualizar un usuario existente.
   *
   * @param userRequestDTO contiene la información del usuario a actualizar
   * @return UserResponseDTO actualizado en el cuerpo de la respuesta HTTP
   */
  @PutMapping
  public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserRequestDTO userRequestDTO) {
    return new ResponseEntity<>(userService.updateUser(userRequestDTO.toModel()), HttpStatus.OK);
  }
}

package com.nisum.api.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * DTO para la respuesta con la información de usuarios.
 * Contiene la información saliente del usuario con campos adicionales.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserResponseDTO {
  private UUID id;
  private String name;
  private String email;
  private String password;
  private List<PhoneResponseDTO> phones;
  private LocalDate created;
  private LocalDate modified;
  private LocalDate lastLogin;
  private String token;
  private Boolean isActive;
}

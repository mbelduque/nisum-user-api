package com.nisum.api.dto.response;

import com.nisum.api.model.Phone;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserResponseDTO {
  private UUID id;
  private String name;
  private String email;
  private String password;
  private List<Phone> phones;
  private LocalDate created;
  private LocalDate modified;
  private LocalDate lastLogin;
  private String token;
  private Boolean isActive;
}

package com.nisum.api.dto.request;

import com.nisum.api.model.Phone;
import com.nisum.api.model.User;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;

import java.util.List;

@Data
public class UserRequestDTO {
  private String userId;
  @NotNull
  @NotEmpty
  private String name;
  @NotNull
  @NotEmpty
  private String email;
  @NotNull
  @NotEmpty
  private String password;
  @NotNull
  @NotEmpty
  private List<Phone> phones;

  public User toModel() {
    return User.builder()
        .id(userId != null ? userId : null)
        .name(name)
        .email(email)
        .password(password)
        .phones(phones)
        .build();
  }
}

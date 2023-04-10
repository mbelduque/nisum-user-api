package com.nisum.api.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa la entidad User en la base de datos.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
  @Id
  private String id;

  @Column(name = "name")
  private String name;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PhoneEntity> phones = new ArrayList<>();

  @Column(name = "created")
  private LocalDate created;

  @Column(name = "modified")
  private LocalDate modified;

  @Column(name = "last_login")
  private LocalDate lastLogin;

  @Column(name = "token")
  private String token;

  @Column(name = "is_active")
  private Boolean isActive;
}
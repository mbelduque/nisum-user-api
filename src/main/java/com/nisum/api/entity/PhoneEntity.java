package com.nisum.api.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

/**
 * Clase que representa la entidad Phone en la base de datos.
 */
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "phones")
public class PhoneEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "number")
  private String number;

  @Column(name = "citycode")
  private String cityCode;

  @Column(name = "countrycode")
  private String countryCode;

  @ManyToOne(targetEntity = UserEntity.class)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private UserEntity user;
}

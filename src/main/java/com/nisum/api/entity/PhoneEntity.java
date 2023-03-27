package com.nisum.api.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;

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

  @ManyToOne(fetch = FetchType.LAZY)
  private UserEntity userEntity;
}

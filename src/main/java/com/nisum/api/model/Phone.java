package com.nisum.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa el modelo teléfono.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Phone {
  private String number;
  private String citycode;
  private String countrycode;
  private String userId;
}

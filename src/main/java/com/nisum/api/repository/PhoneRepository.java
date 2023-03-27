package com.nisum.api.repository;

import com.nisum.api.entity.PhoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para gestionar la entidad PhoneEntity en la base de datos.
 */
public interface PhoneRepository extends JpaRepository<PhoneEntity, Long> {
}

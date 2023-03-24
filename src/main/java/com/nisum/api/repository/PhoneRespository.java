package com.nisum.api.repository;

import com.nisum.api.entity.PhoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRespository extends JpaRepository<PhoneEntity, Long> {
}

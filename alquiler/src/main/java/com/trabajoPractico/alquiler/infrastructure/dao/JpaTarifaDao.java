package com.trabajoPractico.alquiler.infrastructure.dao;

import com.trabajoPractico.alquiler.infrastructure.entity.TarifaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTarifaDao extends JpaRepository<TarifaEntity, Integer> {
}

package com.trabajoPractico.alquiler.infrastructure.dao;

import com.trabajoPractico.alquiler.infrastructure.entity.AlquilerEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JpaAlquilerDao extends JpaRepository<AlquilerEntity, Integer> {
}

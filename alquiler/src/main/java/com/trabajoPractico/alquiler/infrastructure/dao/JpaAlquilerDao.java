package com.trabajoPractico.alquiler.infrastructure.dao;

import com.trabajoPractico.alquiler.domain.model.Alquiler;
import com.trabajoPractico.alquiler.infrastructure.entity.AlquilerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface JpaAlquilerDao extends JpaRepository<AlquilerEntity, Integer> {
}

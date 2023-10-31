package com.trabajoPractico.estaciones.infrastructure.dao;


import com.trabajoPractico.estaciones.infrastructure.entity.EstacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaEstacionDao extends JpaRepository<EstacionEntity, Integer> {
}

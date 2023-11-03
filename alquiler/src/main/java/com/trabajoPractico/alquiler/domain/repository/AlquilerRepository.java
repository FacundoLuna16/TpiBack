package com.trabajoPractico.alquiler.domain.repository;

import com.trabajoPractico.alquiler.domain.model.Alquiler;
import com.trabajoPractico.alquiler.infrastructure.entity.AlquilerEntity;

import java.util.List;
import java.util.Optional;

public interface AlquilerRepository {

    List<Alquiler> findAll();

    Optional<Alquiler> getById(int alquilerId);

    Optional<Alquiler> save(Alquiler alquiler);

    Optional<Alquiler> update(Alquiler alquiler);

}

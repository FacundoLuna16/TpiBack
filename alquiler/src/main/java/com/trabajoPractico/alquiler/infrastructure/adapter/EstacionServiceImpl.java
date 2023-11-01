package com.trabajoPractico.alquiler.infrastructure.adapter;

import com.trabajoPractico.alquiler.domain.model.Estacion;
import com.trabajoPractico.alquiler.domain.service.EstacionService;

import java.util.Optional;

public class EstacionServiceImpl implements EstacionService {
    @Override
    public Optional<Estacion> getById(int id) {
        return Optional.empty();
    }
}

package com.trabajoPractico.alquiler.infrastructure.adapters;

import com.trabajoPractico.alquiler.domain.model.Estacion;
import com.trabajoPractico.alquiler.domain.exchangePort.EstacionService;

import java.util.Optional;

public class EstacionServiceImpl implements EstacionService {
    @Override
    public Optional<Estacion> getById(int id) {
        return Optional.empty();
    }
}

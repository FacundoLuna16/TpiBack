package com.trabajoPractico.alquiler.domain.service;

import com.trabajoPractico.alquiler.domain.model.Tarifa;
import com.trabajoPractico.alquiler.domain.repository.TarifaRepository;

import java.util.List;

public class DomainTarifaServiceImpl implements TarifaService{
    private final TarifaRepository tarifaRepository;

    public DomainTarifaServiceImpl(TarifaRepository tarifaRepository) {

        this.tarifaRepository = tarifaRepository;
    }

    @Override
    public List<Tarifa> getAll() {
        return tarifaRepository.getAll();
    }
}

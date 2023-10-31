package com.trabajoPractico.alquiler.domain.repository;

import com.trabajoPractico.alquiler.domain.model.Tarifa;

import java.util.List;

public interface TarifaRepository {

    List<Tarifa> getAll();
}

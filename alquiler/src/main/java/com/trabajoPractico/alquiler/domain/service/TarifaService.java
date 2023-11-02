package com.trabajoPractico.alquiler.domain.service;

import com.trabajoPractico.alquiler.domain.model.Tarifa;

import java.time.LocalDate;
import java.util.List;

public interface TarifaService {
    List<Tarifa> getAll();

    Tarifa buscarTarifa(LocalDate fecha);
}

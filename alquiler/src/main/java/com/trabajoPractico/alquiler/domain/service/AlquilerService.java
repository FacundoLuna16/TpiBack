package com.trabajoPractico.alquiler.domain.service;

import com.trabajoPractico.alquiler.application.request.Alquiler.AlquilerFinResquestDto;
import com.trabajoPractico.alquiler.application.request.Alquiler.AlquilerRequestDto;
import com.trabajoPractico.alquiler.application.request.Alquiler.FiltrosAlquilerRequestDto;
import com.trabajoPractico.alquiler.domain.model.Alquiler;

import java.util.List;
import java.util.Optional;

public interface AlquilerService {
    List<Alquiler> getAll();

    Optional<Alquiler> getAlquiler(int alquilerId);

    Alquiler terminarAlquiler(int alquilerId, AlquilerFinResquestDto alquilerDetails);

    Optional<Alquiler> createAlquiler(AlquilerRequestDto alquilerRequestDto);

    List<Alquiler> filtrarPorEstado(int estado);
}

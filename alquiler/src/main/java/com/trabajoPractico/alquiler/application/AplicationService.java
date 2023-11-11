package com.trabajoPractico.alquiler.application;

import com.trabajoPractico.alquiler.application.request.Alquiler.AlquilerFinResquestDto;
import com.trabajoPractico.alquiler.application.request.Alquiler.AlquilerRequestDto;
import com.trabajoPractico.alquiler.application.response.AlquilerFinalizadoResponse;
import com.trabajoPractico.alquiler.application.response.AlquilerResponse;
import com.trabajoPractico.alquiler.domain.model.Alquiler;

import java.util.List;
import java.util.Optional;

public interface AplicationService {
    List<Alquiler> getAll();

    Optional<Alquiler> getAlquiler(int alquilerId);

    Optional<AlquilerFinalizadoResponse> terminarAlquiler(int alquilerId, AlquilerFinResquestDto alquilerDetails);

    Optional<Alquiler> createAlquiler(AlquilerRequestDto alquilerRequestDto);

    List<Alquiler> filtrarPorEstado(int estado);
}

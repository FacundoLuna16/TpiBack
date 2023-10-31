package com.trabajoPractico.alquiler.domain.service;

import com.trabajoPractico.alquiler.domain.model.Alquiler;

import java.util.List;
import java.util.Optional;

public interface AlquilerService {
    List<Alquiler> getAll();

    Optional<Alquiler> getAlquiler(int alquilerId);

    Alquiler iniciarAlquiler(Alquiler alquiler);

    Alquiler terminarAlquiler(int alquilerId, Alquiler alquilerDetails);

}

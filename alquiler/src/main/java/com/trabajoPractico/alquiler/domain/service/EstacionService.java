package com.trabajoPractico.alquiler.domain.service;

import com.trabajoPractico.alquiler.domain.model.Estacion;

import java.util.Optional;

public interface EstacionService {
    //TODO completar interfaz
    // define las peticiones que necesites hacerle al micro servicio estacion para que te devuelva estaciones.
    Optional<Estacion> getById(int id);
}

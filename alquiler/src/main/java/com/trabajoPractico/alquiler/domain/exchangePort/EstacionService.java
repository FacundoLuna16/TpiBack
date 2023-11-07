package com.trabajoPractico.alquiler.domain.exchangePort;

import com.trabajoPractico.alquiler.domain.model.Estacion;

import java.util.Optional;


public interface EstacionService {
    Boolean validateIdEstacion(int id);

    Double getDistanciaEntreEstaciones(int idEstacionOrigen, int idEstacionDestino);
}

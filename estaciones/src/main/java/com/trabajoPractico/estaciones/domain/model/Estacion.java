package com.trabajoPractico.estaciones.domain.model;

import com.trabajoPractico.estaciones.application.request.CreadoEstacionRequest;
import com.trabajoPractico.estaciones.infrastructure.entity.EstacionEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Estacion {

    private int id;
    private String nombre;
    private LocalDateTime fechaHoraDeCreacion;
    private Double latitud;
    private Double longitud;

    public Estacion(CreadoEstacionRequest estacionRequest){
        this.nombre = estacionRequest.getNombre();
        this.fechaHoraDeCreacion = LocalDateTime.now();
        this.latitud = estacionRequest.getLatitud();
        this.longitud = estacionRequest.getLongitud();
    }



}

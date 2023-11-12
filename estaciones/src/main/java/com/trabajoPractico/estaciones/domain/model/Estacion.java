package com.trabajoPractico.estaciones.domain.model;

import com.trabajoPractico.estaciones.application.request.CreadoEstacionRequest;
import com.trabajoPractico.estaciones.infrastructure.entity.EstacionEntity;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class Estacion {

    private int id;

    private NombreEstacion nombre;
    private LocalDateTime fechaHoraDeCreacion;

    private Coordenada coordenada;

    public Estacion(CreadoEstacionRequest estacionRequest){
        this.nombre = new NombreEstacion(estacionRequest.getNombre());
        // TODO - Ver si se puede hacer que se guarde la fecha y hora de creacion
        this.fechaHoraDeCreacion = LocalDateTime.now();
        this.coordenada = new Coordenada(estacionRequest.getLatitud(), estacionRequest.getLongitud());
    }


    public Estacion(int id, NombreEstacion nombreEstacion, LocalDateTime localDateTime, Coordenada coordenada) {
        this.id = id;
        this.nombre = nombreEstacion;
        this.fechaHoraDeCreacion = localDateTime;
        this.coordenada = coordenada;
    }
}

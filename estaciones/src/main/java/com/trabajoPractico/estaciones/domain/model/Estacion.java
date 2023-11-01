package com.trabajoPractico.estaciones.domain.model;

import com.trabajoPractico.estaciones.application.request.CreadoEstacionRequest;
import com.trabajoPractico.estaciones.infrastructure.entity.EstacionEntity;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Estacion {

    private int id;

    private NombreEstacion nombre;
    private LocalDateTime fechaHoraDeCreacion;

    private Coordenada coordenada;
//    private Double latitud;
//    private Double longitud;

    public Estacion(CreadoEstacionRequest estacionRequest){
        this.nombre = new NombreEstacion(estacionRequest.getNombre());
        this.fechaHoraDeCreacion = LocalDateTime.now();
        this.coordenada = new Coordenada(estacionRequest.getLatitud(), estacionRequest.getLongitud());
    }



}

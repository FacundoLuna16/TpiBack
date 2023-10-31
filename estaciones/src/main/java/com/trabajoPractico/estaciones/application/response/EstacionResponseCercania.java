package com.trabajoPractico.estaciones.application.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EstacionResponseCercania {
    private int id;
    private String nombre;
    private int distanciaEnMetros;
    private double latitud;
    private double longitud;
}

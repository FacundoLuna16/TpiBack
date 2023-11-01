package com.trabajoPractico.alquiler.domain.model;


import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class Estacion {
    String nombre;
    int id;

    double latitud;
    double longitud;

    public Estacion(int id, String nombre, double latitud, double longitud) {
        this.id = id;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
    }
}

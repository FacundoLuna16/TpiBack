package com.trabajoPractico.estaciones.domain.model;


import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class NombreEstacion {

    String nombre;

    public NombreEstacion(String nombre) {
        if (nombre.length() < 3 || nombre.length() > 20) throw new IllegalArgumentException("Nombre fuera de rango");
        this.nombre = nombre;
    }
}

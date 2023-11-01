package com.trabajoPractico.estaciones.domain.model;


import lombok.Getter;
import lombok.experimental.FieldDefaults;


@Getter
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class NombreEstacion {

    String value;

    public NombreEstacion(String value) {
        if (value.length() < 3 || value.length() > 100) throw new IllegalArgumentException("Nombre fuera de rango");
        this.value = value;
    }
}

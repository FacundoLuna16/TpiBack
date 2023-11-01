package com.trabajoPractico.estaciones.domain.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Coordenada {
    private long lalitud;
    private long longitud;
}

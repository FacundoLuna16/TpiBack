package com.trabajoPractico.alquiler.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tarifa {
    private long id;
    private long tipoTarifa;
    private String definicion;
    private int diaSemana;
    private int diaMes;
    private int mes;
    private int anio;
    private Double montoFijoAlquiler;
    private Double montoMinutoFraccion;
    private Double montoKm;
    private Double montoHora;


}

package com.trabajoPractico.alquiler.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

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


    public LocalDate getFecha() {
        int anio = this.getAnio();
        int mes = this.getMes();
        int diaMes = this.getDiaMes();

        // Utiliza el método estático 'of' para crear un objeto LocalDate.
        return LocalDate.of(anio, mes, diaMes);
    }



}

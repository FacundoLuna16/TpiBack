package com.trabajoPractico.alquiler.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
        return LocalDate.of(anio, mes, diaMes);
    }

    public Double calcularMontoTotal(LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraDevolucion, Double distancia){
        Double montoFijoAlquiler = this.montoFijoAlquiler;
        Double montoAdicionalPorTiempo = this.calcularMontoAdiccionalPorTiempo(fechaHoraInicio,fechaHoraDevolucion);
        Double montoAdicionalPorDistancia = this.calcularMontoAdiccionalPorDistancia(distancia);

        return montoFijoAlquiler + montoAdicionalPorTiempo + montoAdicionalPorDistancia;

    }

    private Double calcularMontoAdiccionalPorDistancia(Double distancia) {
        return distancia * this.montoKm;
    }

    public Double calcularMontoAdiccionalPorTiempo(LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraDevolucion){

        long minutosAlquilados = Duration.between(fechaHoraInicio,fechaHoraDevolucion).toMinutes();

        double montoMinutosFraccionados = 0.0;
        Double montoAdicionalPorTiempo = 0.0;

        if (minutosAlquilados < 31){
            montoMinutosFraccionados = minutosAlquilados * this.montoMinutoFraccion;
            montoAdicionalPorTiempo = montoMinutosFraccionados;
        } else {


            int horasAlquiladas = (minutosAlquilados / 60)<1 ? 1: (int) (minutosAlquilados / 60);

            long minutosRestantes = minutosAlquilados % 60;
            if (minutosRestantes >= 31) {
                horasAlquiladas++;
            } else {
                montoMinutosFraccionados = minutosRestantes * this.montoMinutoFraccion;
            }

            montoAdicionalPorTiempo = horasAlquiladas * this.montoHora + montoMinutosFraccionados;
        }

        return montoAdicionalPorTiempo;
    }

}

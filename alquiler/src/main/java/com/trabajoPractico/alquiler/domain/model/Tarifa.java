package com.trabajoPractico.alquiler.domain.model;

import com.trabajoPractico.alquiler.infrastructure.entity.TarifaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tarifa {
    private long id;
    private long tipoTarifa;
    private String definicion;
    private Integer diaSemana;
    private Integer diaMes;
    private Integer mes;
    private Integer anio;
    private Double montoFijoAlquiler;
    private Double montoMinutoFraccion;
    private Double montoKm;
    private Double montoHora;


    public LocalDate getFecha() {
        if (this.anio == null || this.mes == null || this.diaMes == null) return null;
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

    public TarifaEntity toEntity() {
        TarifaEntity entity = new TarifaEntity();
        entity.setId(this.id);
        entity.setTipoTarifa(this.tipoTarifa);
        entity.setDefinicion(this.definicion);
        entity.setDiaSemana(this.diaSemana);
        entity.setDiaMes(this.diaMes);
        entity.setMes(this.mes);
        entity.setAnio(this.anio);
        entity.setMontoFijoAlquiler(this.montoFijoAlquiler);
        entity.setMontoMinutoFraccion(this.montoMinutoFraccion);
        entity.setMontoKm(this.montoKm);
        entity.setMontoHora(this.montoHora);
        return entity;
    }
}

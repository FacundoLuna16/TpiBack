package com.trabajoPractico.alquiler.domain.model;

import com.trabajoPractico.alquiler.infrastructure.entity.AlquilerEntity;
import com.trabajoPractico.alquiler.infrastructure.entity.MetodosComunes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alquiler {

    private int id;
    private String idCliente;
    private int estado;
    private int estacionRetiro;
    private int estacionDevolucion;
    private LocalDateTime fechaHoraRetiro;
    private LocalDateTime fechaHoraDevolucion;
    private Double monto;
    private Tarifa tarifa;

    public Alquiler(String idCliente, int estacionRetiro) {
        this.idCliente = idCliente;
        this.estacionRetiro = estacionRetiro;
        this.estado = 1;
        this.fechaHoraRetiro = LocalDateTime.now();
        this.fechaHoraDevolucion = null;
        this.estacionDevolucion = 0;
        this.monto = 0.0;
        this.tarifa = null;
    }


    public AlquilerEntity toEntity() {
        AlquilerEntity entity = new AlquilerEntity();
        entity.setId(this.id);
        entity.setIdCliente(this.idCliente);
        entity.setEstado(this.estado);
        entity.setEstacionRetiro(this.estacionRetiro);
        entity.setEstacionDevolucion(this.estacionDevolucion == 0 ? null : this.estacionDevolucion);
        entity.setFechaHoraRetiro(MetodosComunes.formatLocalDateTime(this.fechaHoraRetiro));
        entity.setFechaHoraDevolucion(MetodosComunes.formatLocalDateTime(this.fechaHoraDevolucion));
        entity.setMonto(this.monto);
        entity.setTarifa(this.tarifa != null ? this.tarifa.toEntity() : null);
        return entity;
    }
    public Alquiler finalizarAlquiler(Double distancia, int estacionDevolucion, LocalDateTime fechaHoraDevolucion, Tarifa tarifa) {
        this.estado = 2;
        this.fechaHoraDevolucion = fechaHoraDevolucion;
        this.tarifa = tarifa;
        this.estacionDevolucion = estacionDevolucion;
        this.monto = this.calcularMontoTotal(distancia);
        return this;
    }
    public Double calcularMontoTotal(Double distancia){
        Double montoFijoAlquiler = tarifa.getMontoFijoAlquiler();
        Double montoAdicionalPorTiempo = this.calcularMontoAdiccionalPorTiempo();
        Double montoAdicionalPorDistancia = this.calcularMontoAdiccionalPorDistancia(distancia);

        return montoFijoAlquiler + montoAdicionalPorTiempo + montoAdicionalPorDistancia;

    }

    private Double calcularMontoAdiccionalPorDistancia(Double distancia) {
        return distancia * this.tarifa.getMontoKm();
    }

    public Double calcularMontoAdiccionalPorTiempo(){

        long minutosAlquilados = Duration.between(this.fechaHoraRetiro,this.fechaHoraDevolucion).toMinutes();

        double montoMinutosFraccionados = 0.0;
        Double montoAdicionalPorTiempo = 0.0;

        if (minutosAlquilados < 31){
            montoMinutosFraccionados = minutosAlquilados * this.tarifa.getMontoMinutoFraccion();
            montoAdicionalPorTiempo = montoMinutosFraccionados;
        } else {


            int horasAlquiladas = (minutosAlquilados / 60)<1 ? 1: (int) (minutosAlquilados / 60);

            long minutosRestantes = minutosAlquilados % 60;
            if (minutosRestantes >= 31) {
                horasAlquiladas++;
            } else {
                montoMinutosFraccionados = minutosRestantes *  this.tarifa.getMontoMinutoFraccion();
            }

            montoAdicionalPorTiempo = horasAlquiladas * this.tarifa.getMontoHora() + montoMinutosFraccionados;
        }

        return montoAdicionalPorTiempo;
    }


}

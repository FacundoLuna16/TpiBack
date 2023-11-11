package com.trabajoPractico.alquiler.domain.model;

import com.trabajoPractico.alquiler.infrastructure.entity.AlquilerEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private long idTarifa;

    public Alquiler(AlquilerEntity entity){
        this.id = entity.getId();
        this.idCliente = entity.getIdCliente();
        this.estado = entity.getEstado();
        this.estacionRetiro = entity.getEstacionRetiro();
        this.estacionDevolucion = entity.getEstacionDevolucion()==null?0:entity.getEstacionDevolucion();
        this.fechaHoraRetiro = entity.getFechaHoraRetiro();
        this.fechaHoraDevolucion = entity.getFechaHoraDevolucion();
        this.monto = entity.getMonto();
        this.idTarifa = entity.getIdTarifa()==null?0:entity.getIdTarifa();
    }

    public Alquiler(String idCliente, int estacionRetiro) {
        this.idCliente = idCliente;
        this.estacionRetiro = estacionRetiro;
        this.estado = 1;
        this.fechaHoraRetiro = LocalDateTime.now();
        this.estacionDevolucion = 0;
        this.fechaHoraDevolucion = null;
        this.monto = 0.0;
        this.idTarifa = 0;
    }

    public Alquiler finalizarAlquiler(Double distancia, int estacionDevolucion, LocalDateTime fechaHoraDevolucion, Tarifa tarifa) {
        this.estado = 2;
        this.fechaHoraDevolucion = fechaHoraDevolucion;
        this.idTarifa = tarifa.getId();
        this.estacionDevolucion = estacionDevolucion;
        this.monto = this.calcularMontoTotal(distancia,tarifa);
        return this;
    }
    public Double calcularMontoTotal(Double distancia, Tarifa tarifa){
        Double montoFijoAlquiler = tarifa.getMontoFijoAlquiler();
        Double montoAdicionalPorTiempo = this.calcularMontoAdiccionalPorTiempo(tarifa);
        Double montoAdicionalPorDistancia = this.calcularMontoAdiccionalPorDistancia(distancia, tarifa);

        return montoFijoAlquiler + montoAdicionalPorTiempo + montoAdicionalPorDistancia;

    }

    private Double calcularMontoAdiccionalPorDistancia(Double distancia, Tarifa tarifa) {
        return distancia * tarifa.getMontoKm();
    }

    public Double calcularMontoAdiccionalPorTiempo(Tarifa tarifa){

        long minutosAlquilados = Duration.between(this.fechaHoraRetiro,this.fechaHoraDevolucion).toMinutes();

        double montoMinutosFraccionados = 0.0;
        Double montoAdicionalPorTiempo = 0.0;

        if (minutosAlquilados < 31){
            montoMinutosFraccionados = minutosAlquilados * tarifa.getMontoMinutoFraccion();
            montoAdicionalPorTiempo = montoMinutosFraccionados;
        } else {


            int horasAlquiladas = (minutosAlquilados / 60)<1 ? 1: (int) (minutosAlquilados / 60);

            long minutosRestantes = minutosAlquilados % 60;
            if (minutosRestantes >= 31) {
                horasAlquiladas++;
            } else {
                montoMinutosFraccionados = minutosRestantes * tarifa.getMontoMinutoFraccion();
            }

            montoAdicionalPorTiempo = horasAlquiladas * tarifa.getMontoHora() + montoMinutosFraccionados;
        }

        return montoAdicionalPorTiempo;
    }


}

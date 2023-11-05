package com.trabajoPractico.alquiler.domain.model;

import com.trabajoPractico.alquiler.infrastructure.entity.AlquilerEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public Alquiler finalizarAlquiler(Double montoTotal, int estacionDevolucion, LocalDateTime fechaHoraDevolucion, long idTarifa) {
        this.estado = 2;
        this.monto = montoTotal;
        this.estacionDevolucion = estacionDevolucion;
        this.fechaHoraDevolucion = fechaHoraDevolucion;
        this.idTarifa = idTarifa;
        return this;
    }
}

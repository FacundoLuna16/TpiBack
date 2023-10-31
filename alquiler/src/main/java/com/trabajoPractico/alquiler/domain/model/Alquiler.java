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
    private int idTarifa;

    public Alquiler(AlquilerEntity entity){
        this.id = entity.getId();
        this.idCliente = entity.getIdCliente();
        this.estado = entity.getEstado();
        this.estacionRetiro = entity.getEstacionRetiro();
        this.estacionDevolucion = entity.getEstacionDevolucion();
        this.fechaHoraRetiro = entity.getFechaHoraRetiro();
        this.fechaHoraDevolucion = entity.getFechaHoraDevolucion();
        this.monto = entity.getMonto();
        this.idTarifa = entity.getIdTarifa();

    }

}

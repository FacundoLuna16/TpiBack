package com.trabajoPractico.alquiler.application.response;

import com.trabajoPractico.alquiler.domain.model.Alquiler;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AlquilerFinalizadoResponse {
    private Integer id;
    private String idCliente;
    private Integer estado;
    private Integer idEstacionRetiro;
    private Integer idEstacionDevolucion;
    private LocalDateTime fechaHoraRetiro;
    private LocalDateTime fechaHoraDevolucion;

    private String moneda;
    private Double monto;
    private Long idTarifa;

    public AlquilerFinalizadoResponse(Alquiler alquiler, String moneda) {
        this.id = alquiler.getId();
        this.idCliente = alquiler.getIdCliente();
        this.estado = alquiler.getEstado();
        this.idEstacionRetiro = alquiler.getEstacionRetiro();
        this.idEstacionDevolucion = alquiler.getEstacionDevolucion();
        this.fechaHoraRetiro = alquiler.getFechaHoraRetiro();
        this.fechaHoraDevolucion = alquiler.getFechaHoraDevolucion();
        this.moneda = moneda == null ? "ARS" : moneda;
        this.monto = alquiler.getMonto();
        this.idTarifa = alquiler.getTarifa() != null ? alquiler.getTarifa().getId() : null;
    }
}

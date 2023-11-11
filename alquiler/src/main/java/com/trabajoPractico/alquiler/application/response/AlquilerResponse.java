package com.trabajoPractico.alquiler.application.response;

import com.trabajoPractico.alquiler.domain.model.Alquiler;
import com.trabajoPractico.alquiler.domain.model.Tarifa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlquilerResponse {
    private Integer id;
    private String idCliente;
    private Integer estado;
    private Integer idEstacionRetiro;
    private Integer idEstacionDevolucion;
    private LocalDateTime fechaHoraRetiro;
    private LocalDateTime fechaHoraDevolucion;
    private Double monto;
    private Long idTarifa;

    public AlquilerResponse(Alquiler alquiler) {
        this.id = alquiler.getId();
        this.idCliente = alquiler.getIdCliente();
        this.estado = alquiler.getEstado();
        this.idEstacionRetiro = alquiler.getEstacionRetiro();
        this.idEstacionDevolucion = alquiler.getEstacionDevolucion();
        this.fechaHoraRetiro = alquiler.getFechaHoraRetiro();
        this.fechaHoraDevolucion = alquiler.getFechaHoraDevolucion();
        this.monto = alquiler.getMonto();
        this.idTarifa = alquiler.getTarifa() != null ? alquiler.getTarifa().getId() : null;
    }
}

package com.trabajoPractico.alquiler.application.response;

import com.trabajoPractico.alquiler.domain.model.Alquiler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlquilerResponse {
    private int id;
    private int idCliente;
    private int idEstacionRetiro;
    private int idEstacionDevolucion;
    private LocalDateTime fechaHoraRetiro;
    private LocalDateTime fechaHoraDevolucion;
    private double monto;
    private int idTarifa;

    public AlquilerResponse(Alquiler alquiler) {
        this.id = alquiler.getId();
        this.idCliente = Integer.parseInt(alquiler.getIdCliente());
        this.idEstacionRetiro = alquiler.getEstacionRetiro();
        this.idEstacionDevolucion = alquiler.getEstacionDevolucion();
        this.fechaHoraRetiro = alquiler.getFechaHoraRetiro();
        this.fechaHoraDevolucion = alquiler.getFechaHoraDevolucion();
        this.monto = alquiler.getMonto();
        this.idTarifa = alquiler.getIdTarifa();
    }
}

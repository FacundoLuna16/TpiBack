package com.trabajoPractico.alquiler.application.response;

import com.trabajoPractico.alquiler.domain.model.Tarifa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarifaResponse {
    private Long id;
    private Long tipoTarifa;
    private String definicion;
    private int diaSemana;
    private int diaMes;
    private int mes;
    private int anio;

    public TarifaResponse(Tarifa tarifa) {
        this.id = tarifa.getId();
        this.tipoTarifa = tarifa.getTipoTarifa();
        this.definicion = tarifa.getDefinicion();
        this.diaSemana = tarifa.getDiaSemana();
        this.diaMes = tarifa.getDiaMes();
        this.mes = tarifa.getMes();
        this.anio = tarifa.getAnio();
    }
}

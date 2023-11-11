package com.trabajoPractico.alquiler.infrastructure.adapters.Conversion;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ConversionRequestDto {
    @JsonProperty("moneda_destino")
    private String monedaDestino;
    private Double importe;

    public ConversionRequestDto(String monedaDestino, Double importe) {
        this.monedaDestino = monedaDestino;
        this.importe = importe;
    }
}

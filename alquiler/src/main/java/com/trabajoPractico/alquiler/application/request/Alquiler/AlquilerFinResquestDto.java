package com.trabajoPractico.alquiler.application.request.Alquiler;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AlquilerFinResquestDto {
    @NotNull(message = "la Estacion NO puede ser nulo")//TODO terminar validaciones
    private int estacionDevolucion;
    private String monedaElegida;
}
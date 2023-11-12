package com.trabajoPractico.alquiler.application.request.Alquiler;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AlquilerFinResquestDto {

    @NotNull(message = "la Estacion NO puede ser nulo")
    @Min(value = 1, message = "La estacion de devolucion no es valida")
    private int estacionDevolucion;

    @NotBlank(message = "debe elegir una moneda valida")
    @Pattern(
            regexp = "^(EUR|CLP|BRL|COP|PEN|GBP|USD|ARS)$",
            message = "Debe elegir una Moneda valida")
    private String monedaElegida;


}

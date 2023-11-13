package com.trabajoPractico.estaciones.application.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class CreadoEstacionRequest {

    @NotNull(message = "El nombre no puede ser nulo")
    @NotBlank(message = "No se admiten nombres vacios")
    private String nombre;

    @NotNull(message = "La latitud no puede ser nula")
    @DecimalMin(value = "-90.0", inclusive = true, message = "La latitud esta fuera de rango")
    @DecimalMax(value = "90.0", inclusive = true, message = "La latitud esta fuera de rango")
    private double latitud;

    @NotNull(message = "la longitud no puede ser nula")
    @DecimalMin(value = "-180.0", inclusive = true, message = "La longitud esta fuera de rango")
    @DecimalMax(value = "180.0", inclusive = true, message = "La longitud esta fuera de rango")
    private double longitud;

}

package com.trabajoPractico.alquiler.application.request.Alquiler;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.aspectj.bridge.Message;

@Data
public class AlquilerRequestDto {

    @NotNull(message = "No puede ser null")
    @NotBlank(message = "El id del cliente no puede ser nulo")
    private String idCliente;

    @NotNull(message = "La estacion no puede ser nula")
    @DecimalMin(value = "1", inclusive = true, message = "La longitud esta fuera de rango")
    private int estacionRetiro;
}

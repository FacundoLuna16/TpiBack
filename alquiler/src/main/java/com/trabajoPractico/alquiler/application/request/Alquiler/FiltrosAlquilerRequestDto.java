package com.trabajoPractico.alquiler.application.request.Alquiler;


import lombok.Data;

@Data

public class FiltrosAlquilerRequestDto {
    private Integer estado;
    private String idCliente;
    private Integer idEstacionRetiro;
    private Integer idEstacionDevolucion;
    private Double montoMinimo;



}

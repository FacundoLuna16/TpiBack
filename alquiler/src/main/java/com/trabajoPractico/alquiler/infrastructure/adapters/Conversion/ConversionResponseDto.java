package com.trabajoPractico.alquiler.infrastructure.adapters.Conversion;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ConversionResponseDto {
    private String moneda;
    private Double importe;


}

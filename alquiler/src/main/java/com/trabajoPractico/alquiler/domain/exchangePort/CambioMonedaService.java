package com.trabajoPractico.alquiler.domain.exchangePort;

public interface CambioMonedaService {

    Double obtenerConversion(Double monto, String monedaDestino);
}

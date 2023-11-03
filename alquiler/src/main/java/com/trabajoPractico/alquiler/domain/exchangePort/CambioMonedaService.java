package com.trabajoPractico.alquiler.domain.exchangePort;

public interface CambioMonedaService {


    Double getConversion(Double monto, String monedaOrigen, String monedaDestino);
}

package com.trabajoPractico.alquiler.infrastructure.adapters.Conversion;

import java.util.HashSet;
import java.util.Set;
public class MonedaDestino {
    private String value;
    private static final Set<String> MONEDAS_ACEPTADAS = new HashSet<>();

    static {
        // Inicializar el conjunto de monedas aceptadas
        MONEDAS_ACEPTADAS.add("EUR");
        MONEDAS_ACEPTADAS.add("CLP");
        MONEDAS_ACEPTADAS.add("BRL");
        MONEDAS_ACEPTADAS.add("COP");
        MONEDAS_ACEPTADAS.add("PEN");
        MONEDAS_ACEPTADAS.add("GBP");
        MONEDAS_ACEPTADAS.add("USD");
    }

    public MonedaDestino(String value) {
        // Verificar si la moneda proporcionada está en el conjunto de monedas aceptadas
        if (MONEDAS_ACEPTADAS.contains(value)) {
            this.value = value;
        } else {
            throw new IllegalArgumentException("Moneda no aceptada");
        }
    }
}

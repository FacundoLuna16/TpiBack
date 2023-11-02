package com.trabajoPractico.estaciones.domain.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
@Getter
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@EqualsAndHashCode //Para comparar coordenadas como objetos
public final class Coordenada { //Nadie puede heredar la clase
    double latitud;
    double longitud;

    public Coordenada(double latitud, double longitud) {

        if (latitud < -90 || latitud > 90) throw new IllegalArgumentException("Latitud fuera de rango");
        if (longitud < -180 || longitud > 180) throw new IllegalArgumentException("Longitud fuera de rango");
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public double calcularDistacia(Coordenada coordenada){
        //Calculamos la diferencia entre las latitudes y longitudes
        double diferenciaLatitud = Math.abs(latitud - coordenada.getLatitud()) * 110000;
        double diferenciaLongitud = Math.abs(longitud - coordenada.getLongitud()) * 110000;

        //Calculamos la distancia euclidea
        Double distancia = Math.sqrt(Math.pow(diferenciaLatitud,2) + Math.pow(diferenciaLongitud,2));

        //Retornamos la distancia
        return distancia;

    }
}

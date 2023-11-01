package com.trabajoPractico.estaciones.application.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@RequiredArgsConstructor //Crea un constructor con todos los atributos requeridos
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class EstacionReponseUnique {
    int id;
    String nombre;
    double latitud;
    double longitud;
}

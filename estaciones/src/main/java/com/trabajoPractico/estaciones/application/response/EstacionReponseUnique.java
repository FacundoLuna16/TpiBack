package com.trabajoPractico.estaciones.application.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor //Crea un constructor con todos los atributos requeridos
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class EstacionReponseUnique {
    int id;
    String nombre;
    LocalDateTime fechaHoraDeCreacion;
    double latitud;
    double longitud;
}

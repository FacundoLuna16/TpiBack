package com.trabajoPractico.estaciones.infrastructure.entity;

import com.trabajoPractico.estaciones.domain.model.Estacion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDateTime;

@Entity
@Table(name = "ESTACIONES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstacionEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "estacion_sequence")
    @TableGenerator(
            name = "estacion_sequence",
            table = "sqlite_sequence",
            pkColumnName = "name",
            valueColumnName = "seq",
            pkColumnValue = "ESTACIONES",//propia
            initialValue = 1,
            allocationSize = 1
    )
    private int id;

    private String nombre;

    @Column(name = "FECHA_HORA_CREACION")
    private LocalDateTime fechaHoraDeCreacion;

    private Double latitud;

    private Double longitud;


    // Constructor sin ID para crear una nueva entidad
    public EstacionEntity(String nombre, Double latitud, Double longitud) {
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public static EstacionEntity from(Estacion estacion) {
        return new EstacionEntity(
                estacion.getId(),
                estacion.getNombre(),
                estacion.getFechaHoraDeCreacion(),
                estacion.getLatitud(),
                estacion.getLongitud()
        );
    }

    public Estacion toEstacion() {
        return new Estacion(this.id, this.nombre, this.fechaHoraDeCreacion, this.latitud, this.longitud);
    }
}
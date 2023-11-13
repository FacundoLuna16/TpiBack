package com.trabajoPractico.estaciones.infrastructure.entity;

import com.trabajoPractico.estaciones.domain.model.Coordenada;
import com.trabajoPractico.estaciones.domain.model.Estacion;
import com.trabajoPractico.estaciones.domain.model.NombreEstacion;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Entity
@Table(name = "ESTACIONES")
@Data
@RequiredArgsConstructor
public class EstacionEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "estacion_sequence")
    @TableGenerator(
            name = "estacion_sequence",
            table = "sqlite_sequence",
            pkColumnName = "name",
            valueColumnName = "seq",
            pkColumnValue = "ESTACIONES",
            initialValue = 1,
            allocationSize = 1
    )
    private int id;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "FECHA_HORA_CREACION")
    private String fechaHoraDeCreacion;

    @Column(name = "LATITUD")
    private Double latitud;

    @Column(name = "LONGITUD")
    private Double longitud;






    public EstacionEntity(Estacion estacion) {
        this.id = estacion.getId();
        this.nombre = estacion.getNombre().getValue();
        this.fechaHoraDeCreacion = MetodosComunes.formatLocalDateTime(estacion.getFechaHoraDeCreacion());
        this.latitud = estacion.getCoordenada().getLatitud();
        this.longitud = estacion.getCoordenada().getLongitud();
    }


    public Estacion toEstacion() {
        return new Estacion(this.id, new NombreEstacion(this.nombre), MetodosComunes.parseLocalDateTime(this.fechaHoraDeCreacion), new Coordenada(this.latitud, this.longitud));
    }
}

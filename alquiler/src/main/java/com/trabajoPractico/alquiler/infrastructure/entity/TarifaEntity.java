package com.trabajoPractico.alquiler.infrastructure.entity;

import com.trabajoPractico.alquiler.domain.model.Tarifa;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "TARIFAS")
@AllArgsConstructor
@NoArgsConstructor
public class TarifaEntity {

    @Id
    @GeneratedValue(generator = "tarifa_sequence")
    @TableGenerator(
            name = "tarifa_sequence",
            table = "sqlite_sequence",
            pkColumnName = "name",
            valueColumnName = "seq",
            pkColumnValue = "TARIFAS",//propia
            initialValue = 1,
            allocationSize = 1
    )
    private long id;

    @Column(name = "TIPO_TARIFA")
    private long tipoTarifa;

    private String definicion;

    @Column(name = "DIA_SEMANA")
    private Integer diaSemana;

    @Column(name = "DIA_MES")
    @Nullable
    private Integer diaMes;

    @Nullable
    private Integer mes;

    @Nullable
    private Integer anio;

    @Column(name = "MONTO_FIJO_ALQUILER")
    private Double montoFijoAlquiler;

    @Column(name = "MONTO_MINUTO_FRACCION")
    private Double montoMinutoFraccion;

    @Column(name = "MONTO_KM")
    private Double montoKm;

    @Column(name = "MONTO_HORA")
    private Double montoHora;

    public Tarifa toTarifa() {
        return new Tarifa(
                this.id,
                this.tipoTarifa,
                this.definicion,
                this.diaSemana,
                this.diaMes != null ? this.diaMes : 0,
                this.mes != null ? this.mes : 0,
                this.anio != null ? this.anio : 0,
                this.montoFijoAlquiler,
                this.montoMinutoFraccion,
                this.montoKm,
                this.montoHora);
    }
}

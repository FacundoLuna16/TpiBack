package com.trabajoPractico.alquiler.infrastructure.entity;

import com.trabajoPractico.alquiler.domain.model.Alquiler;
import com.trabajoPractico.alquiler.domain.model.Tarifa;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "ALQUILERES")
@Data
public class AlquilerEntity {
    @Id
    @GeneratedValue(generator = "alquiler_sequence")
    @TableGenerator(
            name = "alquiler_sequence",
            table = "sqlite_sequence",
            pkColumnName = "name",
            valueColumnName = "seq",
            pkColumnValue = "ALQUILERES",//propia
            initialValue = 1,
            allocationSize = 1
    )
    private int id;

    @Column(name = "ID_CLIENTE")
    private String idCliente;

    private int estado;

    @Column(name = "ESTACION_RETIRO")
    private int estacionRetiro;

    @Column(name = "ESTACION_DEVOLUCION")
    private int estacionDevolucion;

    @Column(name = "FECHA_HORA_RETIRO")
    private LocalDateTime fechaHoraRetiro;

    @Column(name = "FECHA_HORA_DEVOLUCION")
    private LocalDateTime fechaHoraDevolucion;

    private Double monto;

    @Column(name = "ID_TARIFA")
    private int idTarifa;

    public AlquilerEntity (Alquiler alquiler){
        
    }


    public AlquilerEntity() {

    }
}

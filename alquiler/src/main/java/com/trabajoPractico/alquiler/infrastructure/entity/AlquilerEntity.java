package com.trabajoPractico.alquiler.infrastructure.entity;
/*
CREATE TABLE ALQUILERES (
	ID INTEGER PRIMARY KEY AUTOINCREMENT,
	ID_CLIENTE VARCHAR2(50),
	ESTADO INTEGER DEFAULT (1),
	ESTACION_RETIRO INTEGER,
	ESTACION_DEVOLUCION INTEGER,
	FECHA_HORA_RETIRO TIMESTAMP DEFAULT (CURRENT_TIMESTAMP),
	FECHA_HORA_DEVOLUCION TIMESTAMP,
	MONTO REAL,
	ID_TARIFA INTEGER,
	CONSTRAINT ALQUILERES_ESTACIONES_FK_RETIRO FOREIGN KEY (ESTACION_RETIRO) REFERENCES ESTACIONES(ID),
	CONSTRAINT ALQUILERES_ESTACIONES_FK_DEVOLUCION FOREIGN KEY (ESTACION_DEVOLUCION) REFERENCES ESTACIONES(ID),
	CONSTRAINT ALQUILERES_TARIFAS_FK FOREIGN KEY (ID_TARIFA) REFERENCES TARIFAS(ID)
)
 */



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
    private Integer estacionRetiro;

    @Column(name = "ESTACION_DEVOLUCION")
    private Integer estacionDevolucion;

    @Column(name = "FECHA_HORA_RETIRO")
    private String fechaHoraRetiro;

    @Column(name = "FECHA_HORA_DEVOLUCION")
    private String fechaHoraDevolucion;

    private Double monto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_TARIFA", referencedColumnName = "ID")
    private TarifaEntity tarifa;


    public AlquilerEntity() {
    }

    public AlquilerEntity
    from (Alquiler alquiler){
        this.idCliente = alquiler.getIdCliente();
        this.estado = alquiler.getEstado();
        this.estacionRetiro = alquiler.getEstacionRetiro();
        this.estacionDevolucion = alquiler.getEstacionDevolucion()==0?null:alquiler.getEstacionDevolucion();
        this.fechaHoraRetiro = MetodosComunes.formatLocalDateTime(alquiler.getFechaHoraRetiro());
        this.fechaHoraDevolucion = MetodosComunes.formatLocalDateTime(alquiler.getFechaHoraDevolucion());
        this.monto = alquiler.getMonto()==0.0?null:alquiler.getMonto();
        this.tarifa = alquiler.getTarifa().toEntity();
        return this;
    }

    public Alquiler toAlquiler() {
        Alquiler alquiler =  new Alquiler();
        alquiler.setId(this.id);
        alquiler.setIdCliente(this.idCliente);
        alquiler.setEstado(this.estado);
        alquiler.setEstacionRetiro(this.estacionRetiro);
        alquiler.setEstacionDevolucion(this.estacionDevolucion == null ? 0 : this.estacionDevolucion);
        alquiler.setFechaHoraRetiro(MetodosComunes.parseLocalDateTime(this.fechaHoraRetiro));
        alquiler.setFechaHoraDevolucion(MetodosComunes.parseLocalDateTime(this.fechaHoraDevolucion));
        alquiler.setMonto(this.monto == null ? 0.0 : this.monto);
        alquiler.setTarifa(this.tarifa != null ? this.tarifa.toTarifa() : null);

        return alquiler;
    }
}

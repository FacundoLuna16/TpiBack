package com.trabajoPractico.estaciones.domain.repository;



import com.trabajoPractico.estaciones.domain.model.Estacion;
import org.springframework.data.convert.PropertyValueConverter;

import java.util.List;
import java.util.Optional;


public interface EstacionRepository {
    List<Estacion> getAll();
    Optional<Estacion> findById(int id);

    Optional<Estacion> save(Estacion estacion);
    void deleteById(int id);

}

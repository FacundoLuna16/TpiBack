package com.trabajoPractico.estaciones.domain.service;



import com.trabajoPractico.estaciones.application.request.CreadoEstacionRequest;
import com.trabajoPractico.estaciones.application.response.EstacionResponseAll;
import com.trabajoPractico.estaciones.application.response.EstacionResponseCercania;
import com.trabajoPractico.estaciones.domain.model.Estacion;

import java.util.List;
import java.util.Optional;

public interface EstacionService {
    List<Estacion> getAll();

    Optional<Estacion> findById(int id);

    Optional<EstacionResponseCercania> getByCercania(double latitud, double longitud);

    Optional<Estacion> save(CreadoEstacionRequest estacion);

    void deleteById(int id);


    Optional<Double> getDistanciaEntreEstaciones(int idEstacion1, int idEstacion2);
}

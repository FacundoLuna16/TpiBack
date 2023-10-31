package com.trabajoPractico.estaciones.domain.service;

import com.trabajoPractico.estaciones.application.request.CreadoEstacionRequest;
import com.trabajoPractico.estaciones.application.response.EstacionResponseAll;
import com.trabajoPractico.estaciones.application.response.EstacionResponseCercania;
import com.trabajoPractico.estaciones.domain.model.Estacion;
import com.trabajoPractico.estaciones.domain.repository.EstacionRepository;

import java.util.List;
import java.util.Optional;

import static java.lang.Math.abs;

public class DomainEstacionService implements EstacionService{

    private final EstacionRepository estacionRepository;

    public DomainEstacionService(EstacionRepository estacionRepository) {
        this.estacionRepository = estacionRepository;
    }

    @Override
    public List<Estacion> getAll() {

        return estacionRepository.getAll();
    }


    @Override
    public Optional<Estacion> findById(int id) {
        return estacionRepository.findById(id);
    }

    /*
    * Para calcular la distancia entre dos estaciones, se considerará simplemente la
distancia euclídea entre ambos puntos y cada grado corresponderá a 110000 m. Se
aclara que este cálculo no es correcto, pero es suficiente para los fines de este
Trabajo Práctico.*/
    @Override
    public Optional<EstacionResponseCercania> getByCercania(double latitud, double longitud) {

        //Traemos todas las estaciones
        List<Estacion> estaciones = estacionRepository.getAll();
        Estacion estacionCercana = null;
        double distancia = 0;
        double distanciaMinima = 0;

        //Por cada estacion calculamos la distancia
        for (Estacion estacion: estaciones){
            double diferenciaLatitud = Math.abs(latitud - estacion.getLatitud()) * 110000;
            double diferenciaLongitud = Math.abs(longitud - estacion.getLongitud()) * 110000;

            //Calculamos la distancia euclidea
            distancia = Math.sqrt(Math.pow(diferenciaLatitud,2) + Math.pow(diferenciaLongitud,2));

            //Si es la primera estacion o la distancia es menor a la minima, la guardamos
            if (distanciaMinima == 0 || distancia < distanciaMinima){
                distanciaMinima = distancia;
                estacionCercana = estacion;
            }
        }
        //Creamos el objeto de respuesta
        EstacionResponseCercania estacionResponseCercania = new EstacionResponseCercania(
                estacionCercana.getId(),
                estacionCercana.getNombre(),
                (int)distanciaMinima, //para no tener decimales
                estacionCercana.getLatitud(),
                estacionCercana.getLongitud()
        );

        return Optional.of(estacionResponseCercania);
    }

    @Override
    public Optional<Estacion> save(CreadoEstacionRequest estacionRequest) {

        //Creamos la estacion de nuestro modelo
        Estacion estacion = new Estacion(estacionRequest);

        //Guardamos la estacion en la base
        return estacionRepository.save(estacion);
    }


    @Override
    public void deleteById(int id) {
        estacionRepository.deleteById(id);
    }

}

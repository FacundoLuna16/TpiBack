package com.trabajoPractico.estaciones.domain.service;

import com.trabajoPractico.estaciones.application.controller.EstacionController;
import com.trabajoPractico.estaciones.application.request.CreadoEstacionRequest;
import com.trabajoPractico.estaciones.application.response.EstacionResponseCercania;
import com.trabajoPractico.estaciones.domain.model.Coordenada;
import com.trabajoPractico.estaciones.domain.model.Estacion;
import com.trabajoPractico.estaciones.domain.repository.EstacionRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DomainEstacionService implements EstacionService{

    private final EstacionRepository estacionRepository;
    private static final Logger logger = LoggerFactory.getLogger(DomainEstacionService.class);
    public DomainEstacionService(EstacionRepository estacionRepository) {
        this.estacionRepository = estacionRepository;
    }

    @Override
    public List<Estacion> getAll() {

        return estacionRepository.getAll();
    }


    @Override
    public Optional<Estacion> findById(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("El id no puede ser negativo");
        }
        return estacionRepository.findById(id);
    }

    @Override
    public Optional<EstacionResponseCercania> getByCercania(double latitud, double longitud) {
        logger.info("Buscando estación cercana a latitud: {}, longitud: {}", latitud, longitud);

        List<Estacion> estaciones = estacionRepository.getAll();
        if (estaciones.isEmpty()) {
            logger.info("No hay estaciones en la base de datos.");
            return Optional.empty();
        }

        Estacion estacionCercana = null;
        double distancia = 0;
        double distanciaMinima = Double.MAX_VALUE; // Inicializa con un valor grande
        Coordenada cord = new Coordenada(latitud, longitud);

        // Por cada estacion calculamos la distancia
        for (Estacion estacion : estaciones) {
            // Calculamos la distancia euclidiana
            distancia = estacion.getCoordenada().calcularDistancia(cord);
            // Si es la primera estacion o la distancia es menor a la minima, la guardamos
            if (distancia < distanciaMinima) {
                distanciaMinima = distancia;
                estacionCercana = estacion;
            }
        }

        if (estacionCercana == null) {
            logger.info("No se encontró ninguna estación cercana a latitud: {}, longitud: {}", latitud, longitud);
            return Optional.empty();
        }

        // Creamos el objeto de respuesta
        EstacionResponseCercania estacionResponseCercania = new EstacionResponseCercania(
                estacionCercana.getId(),
                estacionCercana.getNombre().getValue(),
                (int) distanciaMinima, // para no tener decimales
                estacionCercana.getCoordenada().getLatitud(),
                estacionCercana.getCoordenada().getLongitud()
        );

        logger.info("Estación cercana encontrada a latitud: {}, longitud: {}. Estación ID: {}, Distancia: {}",
                latitud, longitud, estacionResponseCercania.getId(), distanciaMinima);

        return Optional.of(estacionResponseCercania);
    }


    @Override
    public Optional<Estacion> save(CreadoEstacionRequest estacionRequest) {
        logger.debug("Intentando guardar nueva estación con la siguiente solicitud: {}", estacionRequest);

        if (estacionRequest == null) {
            logger.error("La solicitud de estación no puede ser null. No se puede guardar la estación.");
            throw new IllegalArgumentException("La estacion no puede ser null");
        }

        // Creamos la estacion de nuestro modelo
        Estacion estacion = new Estacion(estacionRequest);

        // Guardamos la estacion en la base
        Optional<Estacion> savedEstacion = estacionRepository.save(estacion);

        if (savedEstacion.isPresent()) {
            logger.debug("Estación guardada con éxito. ID: {}", savedEstacion.get().getId());
        } else {
            logger.error("Error al intentar guardar la estación en la base de datos. Solicitud: {}", estacionRequest);
        }

        return savedEstacion;
    }



    @Override
    public void deleteById(int id) {
        logger.debug("Intentando eliminar estación con ID: {}", id);

        if (id < 0) {
            logger.error("El ID no puede ser negativo. No se puede eliminar la estación.");
            throw new IllegalArgumentException("El id no puede ser negativo");
        }

        estacionRepository.deleteById(id);

        logger.info("Estación eliminada con éxito. ID: {}", id);
    }


    @Override
    public Optional<Double> getDistanciaEntreEstaciones(int idEstacion1, int idEstacion2) {
        logger.debug("Calculando distancia entre estaciones con ID: {} y {}", idEstacion1, idEstacion2);

        if (idEstacion1 < 0 || idEstacion2 < 0) {
            logger.error("El ID no puede ser negativo. No se puede calcular la distancia.");
            throw new IllegalArgumentException("El id no puede ser negativo");
        }

        // Busca las estaciones por ID
        Estacion estacion1 = estacionRepository.findById(idEstacion1).get();
        Estacion estacion2 = estacionRepository.findById(idEstacion2).get();

        // Calcula la distancia entre las estaciones
        Double distanciaMts = estacion1.getCoordenada().calcularDistancia(estacion2.getCoordenada());

        // Pasa la distancia a km con 2 decimales
        Double distanciaKm = (double) Math.round(distanciaMts / 1000 * 100) / 100;

        logger.info("Distancia calculada entre estaciones con ID {} y {}: {} km", idEstacion1, idEstacion2, distanciaKm);

        return Optional.of(distanciaKm);
    }


}

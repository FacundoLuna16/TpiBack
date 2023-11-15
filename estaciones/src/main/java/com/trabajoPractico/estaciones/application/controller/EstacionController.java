package com.trabajoPractico.estaciones.application.controller;

import com.trabajoPractico.estaciones.application.request.CreadoEstacionRequest;
import com.trabajoPractico.estaciones.application.response.EstacionReponseUnique;
import com.trabajoPractico.estaciones.application.response.EstacionResponseAll;
import com.trabajoPractico.estaciones.application.response.EstacionResponseCercania;
import com.trabajoPractico.estaciones.domain.model.Estacion;
import com.trabajoPractico.estaciones.domain.service.EstacionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("api/v1/estaciones")
public class EstacionController {

    private final EstacionService estacionService;
    private static final Logger logger = LoggerFactory.getLogger(EstacionController.class);

    public EstacionController(EstacionService estacionService) {
        this.estacionService = estacionService;
    }

    @GetMapping
    public ResponseEntity<?> Estacion() {
        try {
            logger.info("Iniciando el manejo de la solicitud para obtener todas las estaciones.");

            List<EstacionResponseAll> estacionesAll = estacionService.getAll()
                    .stream()
                    .map(estacion -> new EstacionResponseAll(estacion.getId(), estacion.getNombre().getValue()))
                    .toList();

            if (estacionesAll.isEmpty()) {
                logger.warn("No hay estaciones en la base de datos.");
                return new ResponseEntity<>("No hay estaciones en la base", HttpStatus.NO_CONTENT);
            }

            logger.debug("Se encontraron {} estaciones.", estacionesAll.size());
            return ResponseEntity.ok(estacionesAll);
        } catch (Exception e) {
            logger.error("Error interno al procesar la solicitud de obtener todas las estaciones.", e);
            return new ResponseEntity<>("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getEstacionById(@PathVariable("id") int id) {
        try {
            if (id <= 0) {
                logger.warn("Se recibió una solicitud con un ID no válido: {}", id);
                return new ResponseEntity<>("El ID no es válido", HttpStatus.BAD_REQUEST);
            }

            logger.info("Buscando la estación con ID: {}", id);
            EstacionReponseUnique estacionBuscada = estacionService.findById(id)
                    .map(estacion -> new EstacionReponseUnique(
                            estacion.getId(),
                            estacion.getNombre().getValue(),
                            estacion.getFechaHoraDeCreacion(),
                            estacion.getCoordenada().getLatitud(),
                            estacion.getCoordenada().getLongitud()
                    ))
                    .orElse(new EstacionReponseUnique(0, "no existe", LocalDateTime.now(), 0, 0));

            // Validar si la estación existe
            if (estacionBuscada.getId() == 0) {
                logger.info("No se encontró la estación con ID: {}", id);
                return new ResponseEntity<>("No se encontró la estación", HttpStatus.NO_CONTENT);
            }

            logger.info("Estación encontrada con ID: {}", id);
            return ResponseEntity.ok(estacionBuscada);

        } catch (Exception e) {
            logger.error("Error interno al procesar la solicitud de obtener la estación con ID: {}", id, e);
            return new ResponseEntity<>("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/cercania")
    public ResponseEntity<?> getEstacionByCercania(@RequestParam("latitud") double latitud, @RequestParam("longitud") double longitud) {
        try {
            // Validar que la latitud esté entre -90 y 90 y la longitud entre -180 y 180
            if (latitud < -90 || latitud > 90 || longitud < -180 || longitud > 180) {
                logger.warn("Se recibió una solicitud con latitud o longitud no válidas. Latitud: {}, Longitud: {}", latitud, longitud);
                return new ResponseEntity<>("La latitud o longitud no son válidas", HttpStatus.BAD_REQUEST);
            }

            logger.info("Buscando estación cercana a latitud: {}, longitud: {}", latitud, longitud);
            Optional<EstacionResponseCercania> estacionCercana = estacionService.getByCercania(latitud, longitud);

            // Verificar si no hay estaciones en la base de datos
            if (estacionCercana.isEmpty()) {
                logger.info("No se encontró ninguna estación cercana a latitud: {}, longitud: {}", latitud, longitud);
                return new ResponseEntity<>("No se encontró la estación", HttpStatus.NO_CONTENT);
            }

            logger.info("Estación cercana encontrada a latitud: {}, longitud: {}", latitud, longitud);
            return ResponseEntity.ok(estacionCercana.get());

        } catch (Exception e) {
            logger.error("Error interno al procesar la solicitud de obtener estación cercana. Latitud: {}, Longitud: {}", latitud, longitud, e);
            return new ResponseEntity<>("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/distanciaEntreEstaciones")
    public ResponseEntity<?> getDistanciaEntreEstaciones(@RequestParam("idEstacion1") int idEstacion1, @RequestParam("idEstacion2") int idEstacion2) {
        try {
            logger.info("Calculando la distancia entre las estaciones con IDs: {} y {}", idEstacion1, idEstacion2);

            Optional<Double> distanciaEntreEstaciones = estacionService.getDistanciaEntreEstaciones(idEstacion1, idEstacion2);

            if (distanciaEntreEstaciones.isPresent()) {
                logger.info("Distancia entre estaciones con IDs {} y {} calculada: {}", idEstacion1, idEstacion2, distanciaEntreEstaciones.get());
                return ResponseEntity.ok(distanciaEntreEstaciones.get());
            } else {
                logger.info("No se encontró la distancia entre estaciones con IDs: {} y {}", idEstacion1, idEstacion2);
                return new ResponseEntity<>("No se encontró la distancia entre estaciones", HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            logger.error("Error interno al calcular la distancia entre estaciones con IDs: {} y {}", idEstacion1, idEstacion2, e);
            return new ResponseEntity<>("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @PostMapping
    public ResponseEntity<?> registrarEstacion(@Valid @RequestBody CreadoEstacionRequest estacionRequest, BindingResult result) {
        if (result.hasErrors()) {
            logger.warn("Solicitud de registro de estación con errores de validación: {}", result.getFieldError().getDefaultMessage());
            return ResponseEntity.badRequest().body(result.getFieldError().getDefaultMessage());
        }
        try {
            logger.info("Registrando nueva estación: {}", estacionRequest);
            EstacionReponseUnique estacion = estacionService.save(estacionRequest)
                    .map(estacion1 -> new EstacionReponseUnique(
                            estacion1.getId(),
                            estacion1.getNombre().getValue(),
                            estacion1.getFechaHoraDeCreacion(),
                            estacion1.getCoordenada().getLatitud(),
                            estacion1.getCoordenada().getLongitud()
                    ))
                    .orElse(new EstacionReponseUnique(0, "no existe", LocalDateTime.now(), 0, 0));

            logger.info("Estación registrada con éxito. ID: {}", estacion.getId());
            return new ResponseEntity<>(estacion, HttpStatus.CREATED);

        } catch (Exception e) {
            logger.error("Error interno al procesar la solicitud de registro de estación", e);
            return new ResponseEntity<>("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public String deleteEstacion(@PathVariable("id") int id) {
        try {
            logger.info("Eliminando estación con ID: {}", id);

            estacionService.deleteById(id);

            logger.info("Estación eliminada con éxito. ID: {}", id);
            return "Estación eliminada";

        } catch (Exception e) {
            logger.error("Error al eliminar estación con ID: {}", id, e);
            return "Error al eliminar estación";
        }
    }



}

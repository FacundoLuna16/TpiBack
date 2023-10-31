package com.trabajoPractico.estaciones.application.controller;

import com.trabajoPractico.estaciones.application.request.CreadoEstacionRequest;
import com.trabajoPractico.estaciones.application.response.EstacionResponseAll;
import com.trabajoPractico.estaciones.application.response.EstacionResponseCercania;
import com.trabajoPractico.estaciones.domain.model.Estacion;
import com.trabajoPractico.estaciones.domain.service.EstacionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/v1/estaciones")
public class EstacionController {

    private final EstacionService estacionService;

    public EstacionController(EstacionService estacionService) {
        this.estacionService = estacionService;
    }

    @GetMapping
    public ResponseEntity<?> Estacion() {
        try {
            List<EstacionResponseAll> estacionesAll = estacionService.getAll()
                    .stream()
                    .map(estacion -> new EstacionResponseAll(estacion.getId(), estacion.getNombre()))
                    .toList();
            if (estacionesAll.isEmpty()) {
                return new ResponseEntity<>("No hay estaciones en la base", HttpStatus.NO_CONTENT);
            }
            return ResponseEntity.ok(estacionesAll);
        }catch (Exception e) {
            return new ResponseEntity<>("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEstacionById(@PathVariable("id")int id) {
        if (id <= 0) {
            return new ResponseEntity<>("El ID no es válido", HttpStatus.BAD_REQUEST);
        }

        try {
            EstacionResponseAll estacionBuscada = estacionService.findById(id)
                    .map(estacion -> new EstacionResponseAll(estacion.getId(), estacion.getNombre()))
                    .orElse(new EstacionResponseAll(0, "No se encontró la estación"));

            //Validar si la estacion existe
            if (estacionBuscada.getId() == 0) {
                return new ResponseEntity<>("No se encontró la estación", HttpStatus.NO_CONTENT);
            }
            return ResponseEntity.ok(estacionBuscada);

        } catch (Exception e) {
            return new ResponseEntity<>("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }









    @GetMapping("/cercania")
    public ResponseEntity<?> getEstacionByCercania(@RequestParam("latitud") double latitud, @RequestParam("longitud") double longitud) {
        //validar que la latitud sea entre -90 y 90 y la longitud entre -180 y 180
        if (latitud < -90 || latitud > 90 || longitud < -180 || longitud > 180) {
            return new ResponseEntity<>("La latitud o longitud no son válidas", HttpStatus.BAD_REQUEST);
        }

        try {
            Optional<EstacionResponseCercania> estacionCercana = estacionService.getByCercania(latitud, longitud);
            // Por las dudas si no hay estaciones en la base
            if (estacionCercana.isEmpty()) {
                return new ResponseEntity<>("No se encontró la estación", HttpStatus.NO_CONTENT);
            }

            return ResponseEntity.ok(estacionCercana.get());

        } catch (Exception e) {
            return new ResponseEntity<>("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping
    public ResponseEntity<?> registrarEstacion(@Valid @RequestBody CreadoEstacionRequest estacionRequest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getFieldError().getDefaultMessage());
        }
        try {
            Estacion estacion =estacionService.save(estacionRequest).get();
            return new ResponseEntity<>(estacion, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public String deleteEstacion(@PathVariable("id") int id) {
        estacionService.deleteById(id);
        return "Estacion eliminada";
    }


}

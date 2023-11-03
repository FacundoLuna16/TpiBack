package com.trabajoPractico.alquiler.application.controller;

import com.trabajoPractico.alquiler.application.request.Alquiler.AlquilerFinResquestDto;
import com.trabajoPractico.alquiler.application.request.Alquiler.AlquilerRequestDto;
import com.trabajoPractico.alquiler.application.response.AlquilerResponse;
import com.trabajoPractico.alquiler.domain.service.AlquilerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alquiler")
public class AlquilerController {
    private final AlquilerService alquilerService;

    public AlquilerController(AlquilerService alquilerService) { this.alquilerService = alquilerService;}

    @GetMapping
    public ResponseEntity<?> getAll(){
        try {
            List<AlquilerResponse> alquileres = alquilerService.getAll().stream().map(AlquilerResponse::new).toList();
            return ResponseEntity.ok(alquileres);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createAlquiler(@Valid @RequestBody AlquilerRequestDto alquilerRequestDto, BindingResult result){
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getFieldError().getDefaultMessage());
        }
        try {
            return ResponseEntity.ok(alquilerService.createAlquiler(alquilerRequestDto));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAlquiler(@PathVariable("id") int id, @Valid @RequestBody AlquilerFinResquestDto alquilerFinResquestDto, BindingResult result){
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getFieldError().getDefaultMessage());
        }
        try {
            //TODO implementar
            return ResponseEntity.ok(alquilerService.terminarAlquiler(id,alquilerFinResquestDto));
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAlquiler(@PathVariable("id") int id){
        try {
            return ResponseEntity.ok(alquilerService.getAlquiler(id));
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}

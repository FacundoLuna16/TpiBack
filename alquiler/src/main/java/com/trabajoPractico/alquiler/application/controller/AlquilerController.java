package com.trabajoPractico.alquiler.application.controller;

import com.trabajoPractico.alquiler.application.AplicationService;
import com.trabajoPractico.alquiler.application.request.Alquiler.AlquilerFinResquestDto;
import com.trabajoPractico.alquiler.application.request.Alquiler.AlquilerRequestDto;
import com.trabajoPractico.alquiler.application.response.AlquilerResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alquileres")
public class AlquilerController {
    private final AplicationService aplicationService;

    public AlquilerController(AplicationService aplicationService) {
        this.aplicationService = aplicationService;
    }


    @GetMapping
    public ResponseEntity<?> getAll(){
        try {
            List<AlquilerResponse> alquileres = aplicationService.getAll().stream().map(AlquilerResponse::new).toList();
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
            return ResponseEntity.ok(aplicationService.createAlquiler(alquilerRequestDto));
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
            return ResponseEntity.ok(aplicationService.terminarAlquiler(id,alquilerFinResquestDto));
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAlquiler(@PathVariable("id") int id){
        try {
            return ResponseEntity.ok(aplicationService.getAlquiler(id));
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/filtrar/estado/{estado}")
    public ResponseEntity<?> filtrarAlquileres(@RequestParam int estado){
        try {
            return ResponseEntity.ok(aplicationService.filtrarPorEstado(estado));
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

package com.trabajoPractico.alquiler.application.controller;

import com.trabajoPractico.alquiler.application.request.Alquiler.AlquilerRequestDto;
import com.trabajoPractico.alquiler.application.response.AlquilerResponse;
import com.trabajoPractico.alquiler.domain.service.AlquilerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/alquiler")
public class AlquilerController {
    private final AlquilerService alquilerService;

    public AlquilerController(AlquilerService alquilerService) { this.alquilerService = alquilerService;}

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(alquilerService.getAll()
                .stream()
                .map(AlquilerResponse::new)
                .toList()
        );
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


}

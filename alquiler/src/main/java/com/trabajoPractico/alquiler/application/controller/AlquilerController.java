package com.trabajoPractico.alquiler.application.controller;

import com.trabajoPractico.alquiler.application.response.AlquilerResponse;
import com.trabajoPractico.alquiler.domain.service.AlquilerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

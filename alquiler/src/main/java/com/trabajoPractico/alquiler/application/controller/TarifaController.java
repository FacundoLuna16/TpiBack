package com.trabajoPractico.alquiler.application.controller;

import com.trabajoPractico.alquiler.application.response.TarifaResponse;
import com.trabajoPractico.alquiler.domain.service.TarifaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tarifas")
public class TarifaController {

    private final TarifaService tarifaService;

    public TarifaController(TarifaService tarifaService) {
        this.tarifaService = tarifaService;
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(tarifaService.getAll().stream()
                .map(TarifaResponse::new)
                .toList());
    }

}

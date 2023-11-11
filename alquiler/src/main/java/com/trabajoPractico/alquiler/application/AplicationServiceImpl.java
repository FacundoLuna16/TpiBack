package com.trabajoPractico.alquiler.application;

import com.trabajoPractico.alquiler.application.request.Alquiler.AlquilerFinResquestDto;
import com.trabajoPractico.alquiler.application.request.Alquiler.AlquilerRequestDto;
import com.trabajoPractico.alquiler.application.response.AlquilerFinalizadoResponse;
import com.trabajoPractico.alquiler.application.response.AlquilerResponse;
import com.trabajoPractico.alquiler.domain.exchangePort.CambioMonedaService;
import com.trabajoPractico.alquiler.domain.model.Alquiler;
import com.trabajoPractico.alquiler.domain.service.AlquilerService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class AplicationServiceImpl implements AplicationService{

    private final AlquilerService alquilerService;
    private final CambioMonedaService cambioMonedaService;

    public AplicationServiceImpl(AlquilerService alquilerService, CambioMonedaService cambioMonedaService) {
        this.alquilerService = alquilerService;
        this.cambioMonedaService = cambioMonedaService;
    }


    @Override
    public List<Alquiler> getAll() {
        return alquilerService.getAll();
    }

    @Override
    public Optional<Alquiler> getAlquiler(int alquilerId) {
        return alquilerService.getAlquiler(alquilerId);
    }

    @Override
    @Transactional
    public Optional<AlquilerFinalizadoResponse> terminarAlquiler(int alquilerId, AlquilerFinResquestDto alquilerDetails) {

         AlquilerFinalizadoResponse alquilerResponse = alquilerService.terminarAlquiler(alquilerId, alquilerDetails)
                 .stream()
                 .map(alquiler -> new AlquilerFinalizadoResponse(alquiler, alquilerDetails.getMonedaElegida()))
                 .findFirst()
                 .orElseThrow(() -> new RuntimeException("No se pudo terminar el alquiler"));

         //hacemos la conversion de moneda
         Double importe = cambioMonedaService.obtenerConversion(alquilerResponse.getMonto(), alquilerDetails.getMonedaElegida());
         if (importe == null) {
             throw new RuntimeException("No se pudo obtener la conversion");
         }
         //seteamos el nuevo importe
         alquilerResponse.setMonto(importe);

         //devolvemos el alquiler
         return Optional.of(alquilerResponse);

    }

    @Override
    public Optional<Alquiler> createAlquiler(AlquilerRequestDto alquilerRequestDto) {
        return alquilerService.createAlquiler(alquilerRequestDto);
    }

    @Override
    public List<Alquiler> filtrarPorEstado(int estado) {
        return alquilerService.filtrarPorEstado(estado);
    }
}

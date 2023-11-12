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
    public List<AlquilerResponse> getAll() {
        return alquilerService.getAll().stream().map(AlquilerResponse::new).toList();
    }

    @Override
    public AlquilerResponse getAlquiler(int alquilerId) {
        return alquilerService.getAlquiler(alquilerId).map(AlquilerResponse::new)
                .orElseThrow(() -> new RuntimeException("No se encontro el alquiler"));

    }

    @Override
    @Transactional
    public Optional<AlquilerFinalizadoResponse> terminarAlquiler(int alquilerId, AlquilerFinResquestDto alquilerDetails) {
            //obtenemos el alquiler
         AlquilerFinalizadoResponse alquilerResponse = alquilerService.terminarAlquiler(alquilerId, alquilerDetails)
                 .stream()
                 .map(alquiler -> new AlquilerFinalizadoResponse(alquiler, alquilerDetails.getMonedaElegida()))
                 .findFirst()
                 .orElseThrow(() -> new RuntimeException("No se pudo terminar el alquiler"));

         //hacemos la conversion de moneda
        //Si la moneda elegida es ARS, no hacemos la conversion
        if (alquilerDetails.getMonedaElegida().equals("ARS") || alquilerDetails.getMonedaElegida() == null) return Optional.of(alquilerResponse);

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
    public Optional<AlquilerResponse> createAlquiler(AlquilerRequestDto alquilerRequestDto) {
        return alquilerService.createAlquiler(alquilerRequestDto).map(AlquilerResponse::new);
    }

    @Override
    public List<AlquilerResponse> filtrarPorEstado(int estado) {
        return alquilerService.filtrarPorEstado(estado).stream().map(AlquilerResponse::new).toList();
    }
}

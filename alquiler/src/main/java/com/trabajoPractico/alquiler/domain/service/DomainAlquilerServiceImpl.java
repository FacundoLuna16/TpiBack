package com.trabajoPractico.alquiler.domain.service;

import com.trabajoPractico.alquiler.application.request.Alquiler.AlquilerFinResquestDto;
import com.trabajoPractico.alquiler.application.request.Alquiler.AlquilerRequestDto;
import com.trabajoPractico.alquiler.application.request.Alquiler.FiltrosAlquilerRequestDto;
import com.trabajoPractico.alquiler.domain.exchangePort.EstacionService;
import com.trabajoPractico.alquiler.domain.model.Alquiler;
import com.trabajoPractico.alquiler.domain.model.Tarifa;
import com.trabajoPractico.alquiler.domain.repository.AlquilerRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DomainAlquilerServiceImpl implements AlquilerService{

    private final AlquilerRepository alquilerRepository;
    private final EstacionService estacionService;

    private final TarifaService tarifaService;

    public DomainAlquilerServiceImpl(AlquilerRepository alquilerRepository, EstacionService estacionService, TarifaService tarifaService) {
        this.alquilerRepository = alquilerRepository;
        this.estacionService = estacionService;
        this.tarifaService = tarifaService;
    }


    @Override
    public List<Alquiler> getAll() {
        return alquilerRepository.findAll();
    }

    @Override
    public Optional<Alquiler> getAlquiler(int alquilerId) {
        return alquilerRepository.getById(alquilerId);
    }


    @Override
    public Alquiler terminarAlquiler(int alquilerId, AlquilerFinResquestDto alquilerDetails) {

        Optional<Alquiler> alquilerAFinalizar = this.buscarAlquiler(alquilerId);

        //calcular costo del alquiler segun el dia y hora que se devuelve la bicicleta
        LocalDateTime fechaHoraDevolucion = LocalDateTime.now();
        Tarifa tarifa = tarifaService.buscarTarifa(fechaHoraDevolucion);

        //Argumentos necesarios para calcular el monto
        Double montoFijo = tarifa.getMontoFijoAlquiler();
        //calcular cuantas horas pasaron entre la fechaHoraInicio y la fechaHoraDevolucion
        LocalDateTime fechaHoraInicio = alquilerAFinalizar.get().getFechaHoraRetiro();

        Double montoAdicionalPorTiempo = tarifa.calcularMontoAdiccionalPorTiempo(fechaHoraInicio,fechaHoraDevolucion);

        //calcular la distancia entre estaciones
        Double distancia = estacionService.getDistanciaEntreEstaciones(alquilerAFinalizar.get().getEstacionRetiro(),alquilerDetails.getEstacionDevolucion());
        if(distancia == null) throw new RuntimeException("no se encontro una estacion");

        Double montoAdicionalDistancia = tarifa.getMontoKm() * distancia;

        //calcular el monto total

        Double montoTotal = tarifa.calcularMontoTotal(montoAdicionalPorTiempo,montoAdicionalDistancia);

        //Transformacion de moneda









        //Cuando termine de calcular las cosas (xD) creo el alquiler con los datos nuevos
        alquilerAFinalizar.get().setMonto(montoTotal);
        alquilerAFinalizar.get().setFechaHoraDevolucion(fechaHoraDevolucion);
        alquilerAFinalizar.get().setEstado(2);
        alquilerAFinalizar.get().setEstacionDevolucion(alquilerDetails.getEstacionDevolucion());
        alquilerAFinalizar.get().setIdTarifa((int)tarifa.getId());

        alquilerRepository.save(alquilerAFinalizar.get());

        return alquilerRepository.update(alquilerAFinalizar.get()).get();
    }

    private Optional<Alquiler> buscarAlquiler(int alquilerId) {
        //Buscar que exista el alquiler solicitado a finalizar
        Optional<Alquiler> alquilerAFinalizar  = alquilerRepository.getById(alquilerId);

        if (alquilerAFinalizar.isEmpty() || alquilerAFinalizar.get().getEstado() == 2){
            throw new RuntimeException("El alquiler no existe o ya finalizo");
        }
        return alquilerAFinalizar;
    }

    @Override
    public Optional<Alquiler> createAlquiler(AlquilerRequestDto alquilerRequestDto) {
        //Validar que el int de la estacion exista
        if (!estacionService.validateIdEstacion(alquilerRequestDto.getIdEstacionRetiro())) {
            throw new RuntimeException("La estacion de retiro no existe");
        }
        //crear alquiler con los datos necesarios
        Alquiler alquiler = new Alquiler(alquilerRequestDto.getIdCliente(),alquilerRequestDto.getIdEstacionRetiro());

        //Guarda el alquieler en la base de datos
        return alquilerRepository.save(alquiler);
    }

    @Override
    public List<Alquiler> filtrarPorEstado(int estado) {

        return alquilerRepository.findAll().stream()
                .filter(alquiler -> alquiler.getEstado() == estado)
                .collect(Collectors.toList());
    }


}

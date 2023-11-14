package com.trabajoPractico.alquiler.domain.service;

import com.trabajoPractico.alquiler.application.request.Alquiler.AlquilerFinResquestDto;
import com.trabajoPractico.alquiler.application.request.Alquiler.AlquilerRequestDto;
import com.trabajoPractico.alquiler.domain.exchangePort.EstacionService;
import com.trabajoPractico.alquiler.domain.model.Alquiler;
import com.trabajoPractico.alquiler.domain.model.Tarifa;
import com.trabajoPractico.alquiler.domain.repository.AlquilerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        if (alquilerId <= 0) throw new RuntimeException("El id del alquiler no puede ser menor o igual a 0");

        Optional<Alquiler> alquiler = alquilerRepository.getById(alquilerId);

        return alquiler;
    }


    @Override
    public Optional<Alquiler> terminarAlquiler(int alquilerId, AlquilerFinResquestDto alquilerDetails) {
        if (alquilerId <= 0) throw new RuntimeException("El id del alquiler no puede ser menor o igual a 0");
        Optional<Alquiler> alquilerAFinalizar = this.buscarAlquiler(alquilerId);

        //Buscamos la tarifa que corresponde a la fechaHoraDevolucion
        LocalDateTime fechaHoraDevolucion = LocalDateTime.now();
        Tarifa tarifa = tarifaService.buscarTarifa(fechaHoraDevolucion);

        //calcular la distancia entre estaciones
        Double distancia = estacionService.getDistanciaEntreEstaciones(alquilerAFinalizar.get().getEstacionRetiro(),alquilerDetails.getEstacionDevolucion());
        if(distancia == null) throw new RuntimeException("no se encontro una estacion");


        alquilerAFinalizar.get().finalizarAlquiler(distancia,alquilerDetails.getEstacionDevolucion(),fechaHoraDevolucion,tarifa);
        //Cuando termine de calcular las cosas creo el alquiler con los datos nuevos
        return alquilerRepository.update(alquilerAFinalizar.get());
    }

    private Optional<Alquiler> buscarAlquiler(int alquilerId) {
        //TODO implementar con getAlquiler? justifica el uso de un metodo aparte?

        if (alquilerId <= 0) throw new RuntimeException("El id del alquiler no puede ser menor o igual a 0");
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

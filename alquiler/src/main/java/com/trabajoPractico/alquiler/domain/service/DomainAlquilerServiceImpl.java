package com.trabajoPractico.alquiler.domain.service;

import com.trabajoPractico.alquiler.application.request.Alquiler.AlquilerRequestDto;
import com.trabajoPractico.alquiler.domain.exchangePort.EstacionService;
import com.trabajoPractico.alquiler.domain.model.Alquiler;
import com.trabajoPractico.alquiler.domain.repository.AlquilerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DomainAlquilerServiceImpl implements AlquilerService{

    private final AlquilerRepository alquilerRepository;
    private final EstacionService estacionService;

    public DomainAlquilerServiceImpl(AlquilerRepository alquilerRepository, EstacionService estacionService) {
        this.alquilerRepository = alquilerRepository;
        this.estacionService = estacionService;
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
    public Alquiler iniciarAlquiler(Alquiler alquiler) {

        return null;
    }

    @Override
    public Alquiler terminarAlquiler(int alquilerId, Alquiler alquilerDetails) {

        return null;
    }

    @Override
    public Optional<Alquiler> createAlquiler(AlquilerRequestDto alquilerRequestDto) {
        //Validar que el int de la estacion exista
        if (!estacionService.validateIdEstacion(alquilerRequestDto.getIdEstacionRetiro())) {
            throw new RuntimeException("La estacion de retiro no existe");
        }

        //crear alquiler con los datos necesarios
        Alquiler alquiler = new Alquiler(alquilerRequestDto.getIdCliente(),alquilerRequestDto.getIdEstacionRetiro());


        //Guarda el alquieler
        //TODO completar metodo






        return Optional.empty();
    }
}

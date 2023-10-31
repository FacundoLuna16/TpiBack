package com.trabajoPractico.alquiler.domain.service;

import com.trabajoPractico.alquiler.domain.model.Alquiler;
import com.trabajoPractico.alquiler.domain.repository.AlquilerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DomainAlquilerServiceImpl implements AlquilerService{

    private final AlquilerRepository alquilerRepository;

    public DomainAlquilerServiceImpl(AlquilerRepository alquilerRepository) {
        this.alquilerRepository = alquilerRepository;
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
}

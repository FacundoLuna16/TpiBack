package com.trabajoPractico.estaciones.infrastructure.repository;

import com.trabajoPractico.estaciones.domain.model.Estacion;
import com.trabajoPractico.estaciones.domain.repository.EstacionRepository;
import com.trabajoPractico.estaciones.infrastructure.dao.JpaEstacionDao;
import com.trabajoPractico.estaciones.infrastructure.entity.EstacionEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;

@Component
public class JpaEstacionRepository implements EstacionRepository {

    private final JpaEstacionDao estacionDao;

    @Autowired
    public JpaEstacionRepository(JpaEstacionDao estacionDao) {
        this.estacionDao = estacionDao;
    }

    @Override
    public List<Estacion> getAll() {

        return estacionDao.findAll()
                .stream()
                .map(EstacionEntity::toEstacion)
                .toList();
    }

    @Override
    public Optional<Estacion> findById(int id) {
        return estacionDao.findById(id)
                .map(EstacionEntity::toEstacion);
    }

    @Override
    public Optional<Estacion> save(Estacion estacion) {

        EstacionEntity estacionEntity = estacionDao.save(EstacionEntity.from(estacion));
        return Optional.of(estacionEntity.toEstacion());
    }

    @Override
    public void deleteById(int id) {
        estacionDao.deleteById(id);
    }

}

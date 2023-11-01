package com.trabajoPractico.alquiler.infrastructure.repository;

import com.trabajoPractico.alquiler.domain.model.Alquiler;
import com.trabajoPractico.alquiler.domain.repository.AlquilerRepository;
import com.trabajoPractico.alquiler.infrastructure.dao.JpaAlquilerDao;
import com.trabajoPractico.alquiler.infrastructure.entity.AlquilerEntity;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class JpaAlquilerRepository implements AlquilerRepository {

    private final JpaAlquilerDao alquilerDao;

    public JpaAlquilerRepository(JpaAlquilerDao alquilerDao) {
        this.alquilerDao = alquilerDao;
    }


    @Override
    public List<Alquiler> findAll() {
        return alquilerDao.findAll().stream().map(Alquiler::new).toList();
    }

    @Override
    public Optional<Alquiler> getById(int alquilerId) {
        AlquilerEntity alquilerEntity = alquilerDao.getById(alquilerId);

        //TODO Mapeo de entity a Modelo de dominio RESOLVER
        Alquiler alquiler = new Alquiler(alquilerEntity);

        return Optional.of(alquiler);
    }

    @Override
    @Transactional
    public Optional<Alquiler> save(Alquiler alquiler) {
        AlquilerEntity alquilerEntity = alquilerDao.save(new AlquilerEntity(alquiler));

        //Transformar alquilerEntity a Alquiler para devolverlo
        Alquiler alquiler1 = new Alquiler(alquilerEntity);
        return Optional.of(alquiler1);
    }

    @Override
    public Optional<Alquiler> update(Alquiler alquiler) {
        return Optional.empty();
    }
}

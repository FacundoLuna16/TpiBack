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
        return alquilerDao.findAll().stream().map(AlquilerEntity::toAlquiler).toList();
    }

    @Override
    public Optional<Alquiler> getById(int alquilerId) {
        AlquilerEntity alquilerEntity = alquilerDao.findById(alquilerId).get();

        Alquiler alquiler = alquilerEntity.toAlquiler();

        return Optional.of(alquiler);
    }

    @Override
    @Transactional
    public Optional<Alquiler> save(Alquiler alquiler) {
        AlquilerEntity alquilerEntity = alquilerDao.save(alquiler.toEntity());

        //Transformar alquilerEntity a Alquiler para devolverlo
        Alquiler alquiler1 = alquilerEntity.toAlquiler();
        return Optional.of(alquiler1);
    }

    @Override
    public Optional<Alquiler> update(Alquiler alquilerNuevosDatos) {
        //busco en la base de datos el alquiler que quiero actualizar
        AlquilerEntity alquilerEntity = alquilerDao.findById(alquilerNuevosDatos.getId()).get();

        alquilerDao.save(alquilerEntity.from(alquilerNuevosDatos));

        Alquiler alquiler1 = alquilerEntity.toAlquiler();
        return Optional.of(alquiler1);
    }


}

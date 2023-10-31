package com.trabajoPractico.alquiler.infrastructure.repository;

import com.trabajoPractico.alquiler.domain.model.Tarifa;
import com.trabajoPractico.alquiler.domain.repository.TarifaRepository;
import com.trabajoPractico.alquiler.infrastructure.dao.JpaTarifaDao;
import com.trabajoPractico.alquiler.infrastructure.entity.TarifaEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JpaTarifaRepository implements TarifaRepository {
    private final JpaTarifaDao tarifaDao;

    public JpaTarifaRepository(JpaTarifaDao tarifaDao) {
        this.tarifaDao = tarifaDao;
    }

    @Override
    public List<Tarifa> getAll() {
        return tarifaDao.findAll()
                .stream()
                .map(TarifaEntity::toTarifa)
                .toList();
    }
}

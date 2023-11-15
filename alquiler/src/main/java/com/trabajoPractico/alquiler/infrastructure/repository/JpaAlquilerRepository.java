package com.trabajoPractico.alquiler.infrastructure.repository;

import com.trabajoPractico.alquiler.domain.model.Alquiler;
import com.trabajoPractico.alquiler.domain.repository.AlquilerRepository;
import com.trabajoPractico.alquiler.infrastructure.dao.JpaAlquilerDao;
import com.trabajoPractico.alquiler.infrastructure.entity.AlquilerEntity;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JpaAlquilerRepository implements AlquilerRepository {

    private final JpaAlquilerDao alquilerDao;
    private static final Logger logger = LoggerFactory.getLogger(JpaAlquilerRepository.class);

    public JpaAlquilerRepository(JpaAlquilerDao alquilerDao) {
        this.alquilerDao = alquilerDao;
    }


    @Override
    public List<Alquiler> findAll() {
        logger.debug("Buscando todos los alquileres.");

        List<AlquilerEntity> alquilerEntities = alquilerDao.findAll();

        logger.info("Encontrados {} alquileres en la base de datos.", alquilerEntities.size());

        return alquilerEntities.stream().map(AlquilerEntity::toAlquiler).toList();
    }


    @Override
    public Optional<Alquiler> getById(int alquilerId) {
        logger.debug("Buscando alquiler con ID: {}", alquilerId);

        AlquilerEntity alquilerEntity = alquilerDao.findById(alquilerId).orElse(null);

        if (alquilerEntity == null) {
            logger.info("No se encontró ningún alquiler con ID: {}", alquilerId);
            return Optional.empty();
        }

        Alquiler alquiler = alquilerEntity.toAlquiler();

        logger.info("Alquiler encontrado con éxito. ID: {}", alquilerId);

        return Optional.of(alquiler);
    }


    @Override
    @Transactional
    public Optional<Alquiler> save(Alquiler alquiler) {
        logger.debug("Intentando guardar alquiler: {}", alquiler);

        if (alquiler == null) {
            logger.error("El alquiler no puede ser null. No se puede guardar.");
            throw new IllegalArgumentException("El alquiler no puede ser null");
        }

        AlquilerEntity alquilerEntity = alquilerDao.save(alquiler.toEntity());

        if (alquilerEntity == null) {
            logger.error("Error al intentar guardar el alquiler en la base de datos. Alquiler: {}", alquiler);
            throw new RuntimeException("Error al intentar guardar el alquiler en la base de datos.");
        }

        // Transformar alquilerEntity a Alquiler para devolverlo
        Alquiler savedAlquiler = alquilerEntity.toAlquiler();

        logger.info("Alquiler guardado con éxito. ID: {}", savedAlquiler.getId());

        return Optional.of(savedAlquiler);
    }


    @Override
    public Optional<Alquiler> update(Alquiler alquilerNuevosDatos) {
        logger.debug("Intentando actualizar alquiler con ID: {}", alquilerNuevosDatos.getId());

        if (alquilerNuevosDatos == null) {
            logger.error("Los nuevos datos del alquiler no pueden ser null. No se puede actualizar.");
            throw new IllegalArgumentException("Los nuevos datos del alquiler no pueden ser null");
        }

        // Busco en la base de datos el alquiler que quiero actualizar
        Optional<AlquilerEntity> optionalAlquilerEntity = alquilerDao.findById(alquilerNuevosDatos.getId());

        if (optionalAlquilerEntity.isEmpty()) {
            logger.error("No se encontró ningún alquiler con ID: {}. No se puede actualizar.", alquilerNuevosDatos.getId());
            return Optional.empty();
        }

        AlquilerEntity alquilerEntity = optionalAlquilerEntity.get();

        alquilerDao.save(alquilerEntity.from(alquilerNuevosDatos));

        // Transformar alquilerEntity a Alquiler para devolverlo
        Alquiler updatedAlquiler = alquilerEntity.toAlquiler();

        logger.info("Alquiler actualizado con éxito. ID: {}", updatedAlquiler.getId());

        return Optional.of(updatedAlquiler);
    }



}

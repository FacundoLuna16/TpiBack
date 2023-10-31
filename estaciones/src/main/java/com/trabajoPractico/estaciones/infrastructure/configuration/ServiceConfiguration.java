package com.trabajoPractico.estaciones.infrastructure.configuration;

import com.trabajoPractico.estaciones.domain.repository.EstacionRepository;
import com.trabajoPractico.estaciones.domain.service.DomainEstacionService;
import com.trabajoPractico.estaciones.domain.service.EstacionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    public EstacionService estacionService(EstacionRepository estacionRepository) {
        return new DomainEstacionService(estacionRepository);
    }
}

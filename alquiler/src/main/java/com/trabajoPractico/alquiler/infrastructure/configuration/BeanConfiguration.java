package com.trabajoPractico.alquiler.infrastructure.configuration;

import com.trabajoPractico.alquiler.domain.repository.AlquilerRepository;
import com.trabajoPractico.alquiler.domain.repository.TarifaRepository;
import com.trabajoPractico.alquiler.domain.service.AlquilerService;
import com.trabajoPractico.alquiler.domain.service.DomainAlquilerServiceImpl;
import com.trabajoPractico.alquiler.domain.service.DomainTarifaServiceImpl;
import com.trabajoPractico.alquiler.domain.service.TarifaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public TarifaService tarifaService(TarifaRepository tarifaRepository) {
        return new DomainTarifaServiceImpl(tarifaRepository);
    }

    @Bean
    public AlquilerService alquilerService(AlquilerRepository alquilerRepository) {
        return new DomainAlquilerServiceImpl(alquilerRepository);
    }

}

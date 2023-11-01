package com.trabajoPractico.alquiler.infrastructure.adapters;

import com.trabajoPractico.alquiler.domain.model.Estacion;
import com.trabajoPractico.alquiler.domain.exchangePort.EstacionService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EstacionServiceImpl implements EstacionService {

    RestTemplate restTemplate;


    @Override
    public Boolean validateIdEstacion(int id) {
        val response = restTemplate.getForEntity("http://localhost:8082/api/v1/estaciones/{id}", EstacionResponse.class, id);

        //validar que el status code sea 200 y que el body no sea null
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            val estacionResponse = response.getBody();
            return true;
        }
        return false;
    }
}

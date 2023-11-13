package com.trabajoPractico.alquiler.infrastructure.adapters;


import com.trabajoPractico.alquiler.domain.exchangePort.EstacionService;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class EstacionServiceImpl implements EstacionService {

    RestTemplate restTemplate;

    final String uriEstacionesById;

    final String uriEstacionesDistanciaEntreEstaciones;

    public EstacionServiceImpl(RestTemplate restTemplate,
                               @Value("${tpi.url-microservicio-estaciones-get-id}") String uriEstacionesById,
                               @Value("${tpi.url-microservicio-estaciones-distancia-estaciones}") String uriEstacionesDistanciaEntreEstaciones) {
        this.restTemplate = restTemplate;
        this.uriEstacionesById = uriEstacionesById;
        this.uriEstacionesDistanciaEntreEstaciones = uriEstacionesDistanciaEntreEstaciones;
    }

    @Override
    public Boolean validateIdEstacion(int id) {

        val response = restTemplate.getForEntity(uriEstacionesById, EstacionResponse.class, id);

        //validar que el status code sea 200 y que el body no sea null
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            val estacionResponse = response.getBody();
            return true;
        }
        return false;
    }

    @Override
    public Double getDistanciaEntreEstaciones(int idEstacionOrigen, int idEstacionDestino) {
        //http://localhost:8081/api/v1/estaciones/distanciaEntreEstaciones?idEstacion1={idEstacionOrigen}&idEstacion2={idEstacionDestino}
        try {

            val response = restTemplate.getForEntity(uriEstacionesDistanciaEntreEstaciones
                    , Double.class, idEstacionOrigen,idEstacionDestino);

            if (response.getStatusCode().is2xxSuccessful()  && response.getBody() != null){
                return response.getBody();
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }
}

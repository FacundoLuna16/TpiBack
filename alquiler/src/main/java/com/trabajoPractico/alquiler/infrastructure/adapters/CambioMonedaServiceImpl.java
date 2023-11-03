package com.trabajoPractico.alquiler.infrastructure.adapters;

import com.trabajoPractico.alquiler.domain.exchangePort.CambioMonedaService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CambioMonedaServiceImpl implements CambioMonedaService {

    RestTemplate restTemplate;

    @Override
    public Double getConversion(Double monto, String monedaOrigen, String monedaDestino) {

        val response = restTemplate.getForEntity("https://api.exchangeratesapi.io/v1/convert\n" +
                "? access_key =8a5ecad044dfb76044e73bd5e45512d8\n" +
                "& from = {monedaOrigen}\n" +
                "& to = {monedaDestino}\n" +
                "& amount = {monto}", ResponseEntity.class,monedaOrigen,monedaDestino,monto);
        //TODO implementar el servicio de cambio de moneda
        return 0.0;
    }
}

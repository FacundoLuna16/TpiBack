package com.trabajoPractico.alquiler.infrastructure.adapters.Conversion;

import com.trabajoPractico.alquiler.domain.exchangePort.CambioMonedaService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CambioMonedaServiceImpl implements CambioMonedaService {

    private final RestTemplate restTemplate;

    @Override
    public Double obtenerConversion(Double monto, String monedaDestino) {

        ConversionRequestDto conversionRequestDto = new ConversionRequestDto(monedaDestino, monto);

        ResponseEntity<ConversionResponseDto> response = restTemplate.postForEntity(
                "http://34.82.105.125:8080/convertir",
                conversionRequestDto,
                ConversionResponseDto.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            ConversionResponseDto conversionResponseDto = response.getBody();
            return conversionResponseDto.getImporte();
        }

        return null;
    }
}

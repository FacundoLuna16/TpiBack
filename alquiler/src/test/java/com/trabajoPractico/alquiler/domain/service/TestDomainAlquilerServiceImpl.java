package com.trabajoPractico.alquiler.domain.service;

import com.trabajoPractico.alquiler.application.request.Alquiler.AlquilerFinResquestDto;
import com.trabajoPractico.alquiler.domain.exchangePort.EstacionService;
import com.trabajoPractico.alquiler.domain.model.Alquiler;
import com.trabajoPractico.alquiler.domain.model.Tarifa;
import com.trabajoPractico.alquiler.domain.repository.AlquilerRepository;
import org.hibernate.mapping.Any;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TestDomainAlquilerServiceImpl {

    @Mock
    private AlquilerRepository alquilerRepository;

    @Mock
    private EstacionService estacionService;

    @Mock
    private TarifaService tarifaService;

    @InjectMocks
    private DomainAlquilerServiceImpl domainAlquilerService;

    private Alquiler alquiler1;

    private Alquiler alquiler2;

    private Tarifa tarifa1;

    @BeforeEach
    void setUp() {
        alquiler1 = new Alquiler("Cliente1", 1);
        alquiler2 = new Alquiler("Cliente2", 7);
        tarifa1 = new Tarifa(1, 1, "S", 1,
                null, null, null, 300.0,
                6.0, 80.0, 240.0);
    }

    ///////////////////////////TEST GETALL//////////////////////////////////////
    @DisplayName("Test para obtener todos los alquileres(lista no vacia)")
    @Test
    void test_getAll_noEmptyList() {
        List<Alquiler> alquileres = Arrays.asList(alquiler1, alquiler2);
        //when
        Mockito.when(alquilerRepository.findAll()).thenReturn(alquileres);

        //Llamamos al metodo a probar
        List<Alquiler> alquileresObtenidos = domainAlquilerService.getAll();

        //verificamos que la lista no sea vacia
        Assertions.assertFalse(alquileresObtenidos.isEmpty());
        Assertions.assertEquals(alquileresObtenidos, alquileres);

        //verificamos que el metodo se haya llamado una vez
        Mockito.verify(alquilerRepository, Mockito.times(1)).findAll();

    }

    @DisplayName("Test para obtener todos los alquileres(lista vacia)")
    @Test
    void test_getAll_emptyList() {
        //hacemos que el repositorio devuelva una lista vacia
        Mockito.when(alquilerRepository.findAll()).thenReturn(Collections.emptyList());

        //entonces al usar el service deberia devolver una lista vacia
        List<Alquiler> alquileresObtenidos = domainAlquilerService.getAll();

        Assertions.assertTrue(alquileresObtenidos.isEmpty());
        Mockito.verify(alquilerRepository, Mockito.times(1)).findAll();


    }

    @DisplayName("Test para manejar excepciones del repositorio para obtener todos los alquileres")
    @Test
    void test_getAll_exception() {
        //hacemos que el repositorio devuelva una exception
        Mockito.when(alquilerRepository.findAll()).thenThrow(RuntimeException.class);

        //vemos como el service maneja la exception
        Assertions.assertThrows(RuntimeException.class, () -> domainAlquilerService.getAll());

        Mockito.verify(alquilerRepository, Mockito.times(1)).findAll();
    }

    ///////////////////////////TEST GET BY ID ALQUILER//////////////////////////////////////

    @DisplayName("Test para obtener un alquiler por id")
    @Test
    void test_getAlquilerById() {
        //when
        Mockito.when(alquilerRepository.getById(1)).thenReturn(Optional.of(alquiler1));

        //Llamamos al metodo a probar
        Optional<Alquiler> alquilerObtenido = domainAlquilerService.getAlquiler(1);

        //verificamos que la lista no sea vacia
        Assertions.assertFalse(alquilerObtenido.isEmpty());
        Assertions.assertEquals(alquilerObtenido.get(), alquiler1);

        //verificamos que el metodo se haya llamado una vez
        Mockito.verify(alquilerRepository, Mockito.times(1)).getById(1);

    }

    @DisplayName("Test para no encontrar un alquiler por id")
    @Test
    void test_getAlquilerById_notFound() {
        //when
        Mockito.when(alquilerRepository.getById(1)).thenReturn(Optional.empty());

        //Llamamos al metodo a probar
        Optional<Alquiler> alquilerObtenido = domainAlquilerService.getAlquiler(1);

        //verificamos que el optional no contenga nada
        Assertions.assertTrue(alquilerObtenido.isEmpty());

        //verificamos que el metodo se haya llamado una vez
        Mockito.verify(alquilerRepository, Mockito.times(1)).getById(1);

    }


    @DisplayName("Test para manejar excepciones del repositorio para obtener un alquiler por id")
    @Test
    void test_getAlquilerById_exception() {
        //hacemos que el repositorio devuelva una exception
        Mockito.when(alquilerRepository.getById(1)).thenThrow(RuntimeException.class);

        //vemos como el service maneja la exception
        Assertions.assertThrows(RuntimeException.class, () -> domainAlquilerService.getAlquiler(1));

        Mockito.verify(alquilerRepository, Mockito.times(1)).getById(1);
    }

    @DisplayName("Test para manejar ID no validos")
    @Test
    void test_getAlquilerById_invalidId() {
        //vemos como el service maneja la exception
        Assertions.assertThrows(RuntimeException.class, () -> domainAlquilerService.getAlquiler(-1));

        Mockito.verify(alquilerRepository, Mockito.times(0)).getById(-1);
    }

    ///////////////////////////TEST TERMINAR ALQUILER//////////////////////////////////////

    @DisplayName("Test para terminar un alquiler")
    @Test
    void test_terminarAlquiler() {
        //when
        alquiler1.setId(1);
        Mockito.when(alquilerRepository.getById(1)).thenReturn(Optional.of(alquiler1));
        //Sin importar el parametro, devuelve la tarifa1
        Mockito.when(tarifaService.buscarTarifa(Mockito.any())).thenReturn(tarifa1);
        Mockito.when(estacionService.getDistanciaEntreEstaciones(1,1)).thenReturn(1.0);
        Mockito.when(alquilerRepository.update(Mockito.any())).thenReturn(Optional.of(alquiler1));
        AlquilerFinResquestDto alquilerFinResquestDto = new AlquilerFinResquestDto(1,"USD");
        //Llamamos al metodo a probar
        Optional<Alquiler> alquilerObtenido = domainAlquilerService.terminarAlquiler(1,alquilerFinResquestDto);

        //verificamos que la lista no sea vacia
        Assertions.assertTrue(alquilerObtenido.isPresent());
        Assertions.assertEquals(alquilerObtenido.get().getId(), alquiler1.getId());


    }

}

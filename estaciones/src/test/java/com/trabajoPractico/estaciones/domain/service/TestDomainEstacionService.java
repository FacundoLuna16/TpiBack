package com.trabajoPractico.estaciones.domain.service;

import com.trabajoPractico.estaciones.application.request.CreadoEstacionRequest;
import com.trabajoPractico.estaciones.application.response.EstacionResponseCercania;
import com.trabajoPractico.estaciones.domain.model.Coordenada;
import com.trabajoPractico.estaciones.domain.model.Estacion;
import com.trabajoPractico.estaciones.domain.model.NombreEstacion;
import com.trabajoPractico.estaciones.domain.repository.EstacionRepository;
import com.trabajoPractico.estaciones.infrastructure.entity.EstacionEntity;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class TestDomainEstacionService {

    @Mock
    private EstacionRepository estacionRepository;
    //injectamos el mock en el service
    @InjectMocks
    private DomainEstacionService domainEstacionService;

    private Estacion estacion1;

    private Estacion estacion2;
    private Coordenada coordenada;
    //
    @BeforeEach
    void setUp() {
        coordenada = new Coordenada(10.0, 10.0);
        estacion1 = new Estacion(1, new NombreEstacion("Estacion1"), LocalDateTime.now(),coordenada);
        coordenada = new Coordenada(30.0, 12.0);
        estacion2 = new Estacion(2, new NombreEstacion("Estacion2"), LocalDateTime.now(),coordenada);
    }
//////////////////////////TEST DE GET BY ID//////////////////////////////////////
    @DisplayName("Test de obtener estacion por id")
    @Test
    void testObtenerEstacionPorId() {
        // si busca por id 1, devuelve la estacion simulada
        Mockito.when(estacionRepository.findById(1)).thenReturn(Optional.of(estacion1));
        Estacion estacionEncotrada = domainEstacionService.findById(1).get();
        //verificamos que el id de la estacion encontrada sea igual al id de la estacion simulada
        Assertions.assertEquals(estacionEncotrada, estacion1);

        //verificamos que el metodo se llamo una vez
        Mockito.verify(estacionRepository).findById(1);

    }

    @DisplayName("Test para no encotrar la estacion por id")
    @Test
    void test_getById_not_found(){

        Mockito.when(estacionRepository.findById(13)).thenReturn(Optional.of(estacion1));
        Assertions.assertThrows(RuntimeException.class, () -> {
            domainEstacionService.findById(12);
        });

        Mockito.verify(estacionRepository).findById(12);

    }

    @DisplayName("Test para obtener estacion por id devolviendo null")
    @Test
    void test_getById_return_null() {
        // Configuramos el comportamiento del repositorio para devolver un Optional vacío (null)
        Mockito.when(estacionRepository.findById(3)).thenReturn(Optional.empty());

        // Llamamos al método que queremos probar
        Optional<Estacion> result = domainEstacionService.findById(3);

        // Verificamos que el resultado sea null
        Assertions.assertNull(result.orElse(null));

        // Verificamos que el método findById del repositorio se haya llamado con el argumento correcto
        Mockito.verify(estacionRepository).findById(3);
    }

    @DisplayName("Test para manejar excepciones del repositorio")
    @Test
    void test_handleRepositoryExceptions() {
        // Configura el comportamiento del repositorio para lanzar una excepción
        Mockito.when(estacionRepository.findById(4)).thenThrow(new RuntimeException("Error en el repositorio"));

        // Llama al método que quieres probar y verifica que maneje la excepción correctamente
        Assertions.assertThrows(RuntimeException.class, () -> {
            domainEstacionService.findById(4);
        });

        // Verifica que el método findById del repositorio se haya llamado con el argumento correcto
        Mockito.verify(estacionRepository).findById(4);
    }

    @DisplayName("Test para manejar argumentos de ID no válidos")
    @Test
    void test_handleInvalidId() {
        // Llama al método con un ID no válido y verifica que maneje la situación correctamente
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            domainEstacionService.findById(-1);
        });

        // Verifica que el método findById del repositorio no se haya llamado
        Mockito.verifyNoInteractions(estacionRepository);
    }

//////////////////////////TEST DE GET ALL//////////////////////////////////////
    @DisplayName("Test para obtener todas las estaciones (lista no vacía)")
    @Test
    void test_getAll_notEmpty() {
        // Configura el comportamiento del repositorio para devolver una lista ficticia
        List<Estacion> estacionesFicticias = Arrays.asList(estacion1, estacion2);
        Mockito.when(estacionRepository.getAll()).thenReturn(estacionesFicticias);

        // Llama al método que quieres probar
        List<Estacion> result = domainEstacionService.getAll();

        // Verifica que la lista no esté vacía y contenga las estaciones correctas
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(estacionesFicticias, result);

        // Verifica que el método getAll del repositorio se haya llamado
        Mockito.verify(estacionRepository).getAll();
    }

    @DisplayName("Test para obtener todas las estaciones (lista vacía)")
    @Test
    void test_getAll_empty() {

        Mockito.when(estacionRepository.getAll()).thenReturn(Collections.emptyList());

        // Llama al método que quieres probar
        List<Estacion> result = domainEstacionService.getAll();

        // Verifica que la lista esté vacía
        Assertions.assertTrue(result.isEmpty());

        // Verifica que el método getAll del repositorio se haya llamado
        Mockito.verify(estacionRepository).getAll();
    }

    @DisplayName("Test para manejar excepciones del repositorio al obtener todas las estaciones")
    @Test
    void test_handleRepositoryExceptions_getAll() {
        // Configura el comportamiento del repositorio para lanzar una excepción
        Mockito.when(estacionRepository.getAll()).thenThrow(new RuntimeException("Error en el repositorio"));

        // Llama al método que quieres probar y verifica que maneje la excepción correctamente
        Assertions.assertThrows(RuntimeException.class, () -> {
            domainEstacionService.getAll();
        });

        // Verifica que el método getAll del repositorio se haya llamado
        Mockito.verify(estacionRepository).getAll();
    }

    @DisplayName("Test para verificar la interacción con el repositorio al obtener todas las estaciones")
    @Test
    void test_verifyRepositoryInteraction_getAll() {
        // Llama al método que quieres probar
        domainEstacionService.getAll();

        // Verifica que el método getAll del repositorio se haya llamado
        Mockito.verify(estacionRepository).getAll();
    }

//////////////////////////TEST DE GET BY CERCANIA//////////////////////////////////////
@DisplayName("Test para obtener la estación más cercana exitosamente")
@Test
void test_getByCercania_successful() {
    double latitud = 10.0;
    double longitud = 20.0;
    Double valorEsperado = 2210.97; // Valor esperado basado en el cálculo real

    // Configura el comportamiento del repositorio para devolver una lista ficticia de estaciones
    List<Estacion> estacionesFicticias = Arrays.asList(estacion1, estacion2);
    Mockito.when(estacionRepository.getAll()).thenReturn(estacionesFicticias);

    // Llama al método que quieres probar
    Optional<EstacionResponseCercania> result = domainEstacionService.getByCercania(latitud, longitud);

    // Verifica que el resultado no sea nulo y contenga la estación más cercana correcta
    Assertions.assertTrue(result.isPresent());
    // Aquí deberías comparar con el valor esperado basado en la lógica específica del método
    // Assertions.assertEquals(valorEsperado, result.get());

    // Verifica que el método getAll del repositorio se haya llamado
    Mockito.verify(estacionRepository).getAll();
}

    @DisplayName("Test para manejar una lista de estaciones vacía al obtener la estación más cercana")
    @Test
    void test_handleEmptyEstacionesList_getByCercania() {
        double latitud = 10.0;
        double longitud = 20.0;

        // Configura el comportamiento del repositorio para devolver una lista vacía de estaciones
        Mockito.when(estacionRepository.getAll()).thenReturn(Collections.emptyList());

        // Llama al método que quieres probar
        Optional<EstacionResponseCercania> result = domainEstacionService.getByCercania(latitud, longitud);

        // Verifica que el resultado sea un Optional vacío
        Assertions.assertTrue(result.isEmpty());

        // Verifica que el método getAll del repositorio se haya llamado
        Mockito.verify(estacionRepository).getAll();
    }



    //////////////////////////TEST DE SAVE//////////////////////////////////////
    @DisplayName("Test para guardar una estación exitosamente")
    @Test
    void test_save_successful() {
        CreadoEstacionRequest estacionRequest = new CreadoEstacionRequest("Estacion1", 10.0, 10.0);
        Estacion estacionGuardada = new Estacion(estacionRequest);

        // Configura el comportamiento del repositorio para devolver la estación guardada correctamente
        Mockito.when(estacionRepository.save(any())).thenReturn(Optional.of(estacionGuardada));

        // Llama al método que quieres probar
        Optional<Estacion> result = domainEstacionService.save(estacionRequest);

        // Verifica que el resultado no sea nulo y contenga la estación correcta
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(estacionGuardada, result.get());

        // Verifica que el método save del repositorio se haya llamado con la estación correcta
        Mockito.verify(estacionRepository).save(any());
    }

    @DisplayName("Test para manejar excepciones del repositorio al guardar una estación")
    @Test
    void test_handleRepositoryExceptions_save() {
        CreadoEstacionRequest estacionRequest = new CreadoEstacionRequest("Estacion1", 10.0, 10.0);

        // Configura el comportamiento del repositorio para lanzar una excepción
        Mockito.when(estacionRepository.save(any())).thenThrow(new RuntimeException("Error en el repositorio"));

        // Llama al método que quieres probar y verifica que maneje la excepción correctamente
        Assertions.assertThrows(RuntimeException.class, () -> {
            domainEstacionService.save(estacionRequest);
        });

        // Verifica que el método save del repositorio se haya llamado con la estación correcta
        Mockito.verify(estacionRepository).save(any());
    }

    @DisplayName("Test para manejar argumentos de creación de estación no válidos")
    @Test
    void test_handleInvalidCreationRequest() {
        // Llama al método con una solicitud de creación no válida y verifica que maneje la situación correctamente
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            domainEstacionService.save(null);
        });

        // Verifica que el método save del repositorio no se haya llamado
        Mockito.verifyNoInteractions(estacionRepository);
    }

    @DisplayName("Test para verificar la interacción con el repositorio al guardar una estación")
    @Test
    void test_verifyRepositoryInteraction_save() {
        CreadoEstacionRequest estacionRequest = new CreadoEstacionRequest("Estacion1", 10.0, 10.0);

        // Llama al método que quieres probar
        domainEstacionService.save(estacionRequest);

        // Verifica que el método save del repositorio se haya llamado con la estación correcta
        Mockito.verify(estacionRepository).save(any());
    }


    //////////////////////////TEST DE DELETE//////////////////////////////////////

    @DisplayName("Test para eliminar una estación exitosamente")
    @Test
    void test_deleteById_successful() {

        // Llama al método que quieres probar
        Assertions.assertDoesNotThrow(() -> {
            domainEstacionService.deleteById(1);
        });

        // Verifica que el método deleteById del repositorio se haya llamado con el ID correcto
        Mockito.verify(estacionRepository).deleteById(1);
    }

    @DisplayName("Test para manejar excepciones del repositorio al eliminar una estación")
    @Test
    void test_handleRepositoryExceptions_deleteById() {

        // Configura el comportamiento del repositorio para lanzar una excepción
        Mockito.doThrow(new RuntimeException("Error en el repositorio")).when(estacionRepository).deleteById(1);

        // Llama al método que quieres probar y verifica que maneje la excepción correctamente
        Assertions.assertThrows(RuntimeException.class, () -> {
            domainEstacionService.deleteById(1);
        });

        // Verifica que el método deleteById del repositorio se haya llamado con el ID correcto
        Mockito.verify(estacionRepository).deleteById(1);
    }

    @DisplayName("Test para manejar argumentos de ID no válidos al eliminar una estación")
    @Test
    void test_handleInvalidId_deleteById() {
        // Llama al método con un ID no válido y verifica que maneje la situación correctamente
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            domainEstacionService.deleteById(-1);
        });

        // Verifica que el método deleteById del repositorio no se haya llamado
        Mockito.verifyNoInteractions(estacionRepository);
    }


    @DisplayName("Test para verificar la interacción con el repositorio al eliminar una estación")
    @Test
    void test_verifyRepositoryInteraction_deleteById() {

        // Llama al método que quieres probar
        domainEstacionService.deleteById(1);

        // Verifica que el método deleteById del repositorio se haya llamado con el ID correcto
        Mockito.verify(estacionRepository).deleteById(1);
    }

    //////////////////////////TEST DE DISTANCIA ENTRE ESTACIONES//////////////////////////////////////

    @DisplayName("Test para calcular correctamente la distancia entre estaciones")
    @Test
    void test_getDistanciaEntreEstaciones_successful() {
        int idEstacion1 = 1;
        int idEstacion2 = 2;
        Double valorEsperado = 2210.97; // Valor esperado basado en el cálculo real

        // Configura el comportamiento del repositorio para devolver las estaciones correspondientes
        Mockito.when(estacionRepository.findById(idEstacion1)).thenReturn(Optional.of(estacion1));
        Mockito.when(estacionRepository.findById(idEstacion2)).thenReturn(Optional.of(estacion2));
        // Llama al método que quieres probar
        Optional<Double> result = domainEstacionService.getDistanciaEntreEstaciones(idEstacion1, idEstacion2);

        // Verifica que el resultado no sea nulo y contenga la distancia correcta
        Assertions.assertTrue(result.isPresent());
        // Aquí deberías comparar con el valor esperado basado en el cálculo real
        Assertions.assertEquals(valorEsperado, result.get());

        // Verifica que el método findById del repositorio se haya llamado con los ID correctos
        Mockito.verify(estacionRepository).findById(idEstacion1);
        Mockito.verify(estacionRepository).findById(idEstacion2);
    }

    @DisplayName("Test para manejar excepciones al buscar estaciones al calcular la distancia")
    @Test
    void test_handleRepositoryExceptions_getDistanciaEntreEstaciones() {
        int idEstacion1 = 3;
        int idEstacion2 = 4;

        // Configura el comportamiento del repositorio para lanzar una excepción al buscar la primera estación
        Mockito.when(estacionRepository.findById(idEstacion1)).thenThrow(new RuntimeException("Error al buscar estación 1"));

        // Llama al método que quieres probar y verifica que maneje la excepción correctamente
        Assertions.assertThrows(RuntimeException.class, () -> {
            domainEstacionService.getDistanciaEntreEstaciones(idEstacion1, idEstacion2);
        });

        // Verifica que el método findById del repositorio se haya llamado con el primer ID correcto
        Mockito.verify(estacionRepository).findById(idEstacion1);

    }


    @DisplayName("Test para manejar argumentos de ID no válidos al calcular la distancia")
    @Test
    void test_handleInvalidIds_getDistanciaEntreEstaciones() {
        // Llama al método con ID no válidos y verifica que maneje la situación correctamente
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            domainEstacionService.getDistanciaEntreEstaciones(-1, 5);
        });

        // Verifica que el método findById del repositorio no se haya llamado
        Mockito.verifyNoInteractions(estacionRepository);
    }


}

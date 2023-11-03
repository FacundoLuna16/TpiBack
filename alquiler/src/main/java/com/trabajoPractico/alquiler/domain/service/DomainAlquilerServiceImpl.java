package com.trabajoPractico.alquiler.domain.service;

import com.trabajoPractico.alquiler.application.request.Alquiler.AlquilerFinResquestDto;
import com.trabajoPractico.alquiler.application.request.Alquiler.AlquilerRequestDto;
import com.trabajoPractico.alquiler.application.request.Alquiler.FiltrosAlquilerRequestDto;
import com.trabajoPractico.alquiler.domain.exchangePort.EstacionService;
import com.trabajoPractico.alquiler.domain.model.Alquiler;
import com.trabajoPractico.alquiler.domain.model.Tarifa;
import com.trabajoPractico.alquiler.domain.repository.AlquilerRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DomainAlquilerServiceImpl implements AlquilerService{

    private final AlquilerRepository alquilerRepository;
    private final EstacionService estacionService;

    private final TarifaService tarifaService;

    public DomainAlquilerServiceImpl(AlquilerRepository alquilerRepository, EstacionService estacionService, TarifaService tarifaService) {
        this.alquilerRepository = alquilerRepository;
        this.estacionService = estacionService;
        this.tarifaService = tarifaService;
    }


    @Override
    public List<Alquiler> getAll() {
        return alquilerRepository.findAll();
    }

    @Override
    public Optional<Alquiler> getAlquiler(int alquilerId) {
        return alquilerRepository.getById(alquilerId);
    }


    @Override
    public Alquiler terminarAlquiler(int alquilerId, AlquilerFinResquestDto alquilerDetails) {
        //Buscar que exista el alquiler solicitado a finalizar
        Optional<Alquiler> alquilerAFinalizar  = alquilerRepository.getById(alquilerId);

        if (alquilerAFinalizar.isEmpty() || alquilerAFinalizar.get().getEstado() == 2){
            throw new RuntimeException("El alquiler no existe o ya finalizo");
        }

        //calcular costo del alquiler segun el dia y hora que se devuelve la bicicleta
        //obtener la fecha y hora actual
        LocalDateTime fechaHoraDevolucion = LocalDateTime.now();

        //obtener solo la fecha
        LocalDate fechaDevolucion = fechaHoraDevolucion.toLocalDate();


        //obtener la tarifa correspondiente
        Tarifa tarifa = tarifaService.buscarTarifa(fechaDevolucion);
        Double montoFijo = tarifa.getMontoFijoAlquiler();


        //calcular cuantas horas pasaron entre la fechaHoraInicio y la fechaHoraDevolucion
        LocalDateTime fechaHoraInicio = alquilerAFinalizar.get().getFechaHoraRetiro();
        long minutosAlquilados = Duration.between(fechaHoraInicio,fechaHoraDevolucion).toMinutes();
        double montoMinutosFraccionados = 0.0;
        Double montoAdicionalHoras = 0.0;
        Double montoAdicionalPorTiempo = 0.0;

        if (minutosAlquilados < 31){
            montoMinutosFraccionados = minutosAlquilados * tarifa.getMontoMinutoFraccion();
            montoAdicionalPorTiempo = montoMinutosFraccionados;
        } else {

            //calcular las horas alquiladas y los minutos sobrantes

            //92 / 60 = 1

            int horasAlquiladas = (minutosAlquilados / 60)<1 ? 1: (int) (minutosAlquilados / 60);
            long minutosRestantes = minutosAlquilados % 60;
            if (minutosRestantes >= 31) {
                horasAlquiladas++;
            } else {
                montoMinutosFraccionados = minutosRestantes * tarifa.getMontoMinutoFraccion();
            }

            montoAdicionalPorTiempo = horasAlquiladas * tarifa.getMontoHora() + montoMinutosFraccionados;
        }

        //calcular la distancia entre estaciones
        Double distancia = estacionService.getDistanciaEntreEstaciones(alquilerAFinalizar.get().getEstacionRetiro(),alquilerDetails.getEstacionDevolucion());
        if(distancia == null) throw new RuntimeException("no se encontro una estacion");

        Double montoAdicionalDistancia = tarifa.getMontoKm() * distancia;


        //calcular el monto total
        Double montoTotal = montoFijo + montoAdicionalPorTiempo + montoAdicionalDistancia;


        //Transformacion de moneda









        //Cuando termine de calcular las cosas (xD) creo el alquiler con los datos nuevos
        alquilerAFinalizar.get().setMonto(montoTotal);
        alquilerAFinalizar.get().setFechaHoraDevolucion(fechaHoraDevolucion);
        alquilerAFinalizar.get().setEstado(2);
        alquilerAFinalizar.get().setEstacionDevolucion(alquilerDetails.getEstacionDevolucion());
        alquilerAFinalizar.get().setIdTarifa((int)tarifa.getId());

        alquilerRepository.save(alquilerAFinalizar.get());

        return alquilerRepository.update(alquilerAFinalizar.get()).get();
    }

    @Override
    public Optional<Alquiler> createAlquiler(AlquilerRequestDto alquilerRequestDto) {
        //Validar que el int de la estacion exista
        if (!estacionService.validateIdEstacion(alquilerRequestDto.getIdEstacionRetiro())) {
            throw new RuntimeException("La estacion de retiro no existe");
        }

        //crear alquiler con los datos necesarios
        Alquiler alquiler = new Alquiler(alquilerRequestDto.getIdCliente(),alquilerRequestDto.getIdEstacionRetiro());


        //Guarda el alquieler en la base de datos
        return alquilerRepository.save(alquiler);
    }

    @Override
    public List<Alquiler> filtrarPorEstado(int estado) {
        List<Alquiler> alquileres = alquilerRepository.findAll();
        List<Alquiler> alquileresFiltrados = new ArrayList<>();

        for (Alquiler alquiler: alquileres) {
            if (alquiler.getEstado() == estado){
                alquileresFiltrados.add(alquiler);
            }
        }
        return alquileresFiltrados;
    }
}

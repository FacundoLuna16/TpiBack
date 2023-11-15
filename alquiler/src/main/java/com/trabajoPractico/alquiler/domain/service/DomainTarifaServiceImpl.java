package com.trabajoPractico.alquiler.domain.service;

import com.trabajoPractico.alquiler.domain.model.Tarifa;
import com.trabajoPractico.alquiler.domain.repository.TarifaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DomainTarifaServiceImpl implements TarifaService{
    private final TarifaRepository tarifaRepository;

    public DomainTarifaServiceImpl(TarifaRepository tarifaRepository) {

        this.tarifaRepository = tarifaRepository;
    }

    @Override
    public List<Tarifa> getAll() {
        return tarifaRepository.getAll();
    }

    @Override
    public Tarifa buscarTarifa(LocalDateTime fechaHoraDevolucion){
        LocalDate fecha = fechaHoraDevolucion.toLocalDate();


        List<Tarifa> tarifas = tarifaRepository.getAll();
        List<Tarifa> tarifasPromocion = tarifas.stream().filter(tarifa -> tarifa.getTipoTarifa() == 2).toList();

        //buscar si es tarifa promocion
        if (tarifasPromocion.size() > 0){
            //recorre todas las tarifas de tipo promocion y pregunta si la fecha es igual a la que se le pasa por parametro
            for (Tarifa tarifa : tarifasPromocion){
                if (tarifa.getFecha().isEqual(fecha)){
                    return tarifa;
                }
            }
        }


        //buscar tarifa por dia de semana
        List<Tarifa> tarifasDiaSemana = tarifas.stream().filter(tarifa -> tarifa.getTipoTarifa() == 1).toList();
        int diaSemana = fecha.getDayOfWeek().getValue();
        if (tarifasDiaSemana.size() > 0){
            //recorre todas las tarifas de tipo promocion y pregunta si la fecha es igual a la que se le pasa por parametro
            for (Tarifa tarifa : tarifasDiaSemana){
                if (tarifa.getDiaSemana() == diaSemana){
                    return tarifa;
                }
            }
        }

        return null;
    }
}

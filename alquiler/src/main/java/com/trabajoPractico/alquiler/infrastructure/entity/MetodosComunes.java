package com.trabajoPractico.alquiler.infrastructure.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MetodosComunes {

    public static LocalDateTime parseLocalDateTime(String fechaHoraDeCreacion) {
        if (fechaHoraDeCreacion == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(fechaHoraDeCreacion, formatter);
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }
}

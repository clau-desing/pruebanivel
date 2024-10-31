package com.pruebanivel.municipio.denuncias.validator;
import com.pruebanivel.municipio.denuncias.entity.*;
import com.pruebanivel.municipio.denuncias.exception.*;


public class DenunciaValidator {
    public static void save(Denuncia registro) {
        // Validar que el título no sea nulo o vacío
        if (registro.getTitulo() == null || registro.getTitulo().trim().isEmpty()) {
            throw new ValidateException("El título es requerido");
        }

        // Validar que el título no exceda los 255 caracteres
        if (registro.getTitulo().length() > 255) {
            throw new ValidateException("El título no debe exceder los 255 caracteres");
        }

        // Validar que la descripción no sea nula o vacía
        if (registro.getDescripcion() == null || registro.getDescripcion().trim().isEmpty()) {
            throw new ValidateException("La descripción es requerida");
        }

        // Validar que la ubicación no sea nula o vacía
        if (registro.getUbicacion() == null || registro.getUbicacion().trim().isEmpty()) {
            throw new ValidateException("La ubicación es requerida");
        }

        // Validar que el estado no sea nulo o vacío
        if (registro.getEstado() == null || registro.getEstado().trim().isEmpty()) {
            throw new ValidateException("El estado es requerido");
        }

        // Validar que el nombre del ciudadano no sea nulo o vacío
        if (registro.getCiudadano() == null || registro.getCiudadano().trim().isEmpty()) {
            throw new ValidateException("El nombre del ciudadano es requerido");
        }

        // Validar que el teléfono del ciudadano no sea nulo o vacío
        if (registro.getTelefono_ciudadano() == null || registro.getTelefono_ciudadano().trim().isEmpty()) {
            throw new ValidateException("El teléfono del ciudadano es requerido");
        }

        // Validar que la fecha de inicio no sea nula
        if (registro.getFechaInicio() == null) {
            throw new ValidateException("La fecha de inicio es requerida");
        }
    }
}


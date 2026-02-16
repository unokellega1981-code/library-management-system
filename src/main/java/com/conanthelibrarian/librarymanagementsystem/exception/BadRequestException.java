package com.conanthelibrarian.librarymanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción que se lanza cuando el cliente envía una petición inválida.
 *
 * <p>Ejemplos típicos:</p>
 * <ul>
 *     <li>Intentar crear un préstamo sin copias disponibles</li>
 *     <li>Enviar datos incoherentes (por ejemplo, fechas inválidas)</li>
 * </ul>
 *
 * <p>Gracias a {@link ResponseStatus}, Spring responderá automáticamente con un
 * HTTP 400 (Bad Request) cuando esta excepción sea lanzada.</p>
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{

    /**
     * Constructor con mensaje personalizado.
     *
     * @param message mensaje de error
     */
    public BadRequestException(String message) {
        super(message);
    }
}

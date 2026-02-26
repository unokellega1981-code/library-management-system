package com.conanthelibrarian.librarymanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción lanzada cuando la solicitud del cliente es inválida
 * o viola una regla de negocio del sistema.
 * <p>
 * Devuelve automáticamente un estado HTTP 400 (BAD_REQUEST)
 * gracias a la anotación @ResponseStatus.
 * <p>
 * Se utiliza en situaciones como:
 * - Intentar prestar un libro sin copias disponibles.
 * - Intentar eliminar un préstamo no devuelto.
 * - Enviar datos inconsistentes o inválidos.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    /**
     * Constructor que recibe el mensaje descriptivo del error.
     *
     * @param message Mensaje detallando la causa del error.
     */
    public BadRequestException(String message) {
        super(message);
    }
}

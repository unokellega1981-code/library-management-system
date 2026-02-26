package com.conanthelibrarian.librarymanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción lanzada cuando un recurso solicitado no existe en el sistema.
 * <p>
 * Se utiliza en la capa de servicio cuando no se encuentra
 * una entidad en la base de datos (por ejemplo, Libro, Usuario o Préstamo).
 * <p>
 * Esta excepción devuelve automáticamente un estado HTTP 404 (NOT_FOUND)
 * gracias a la anotación @ResponseStatus.
 * <p>
 * Ejemplos de uso:
 * - Buscar un libro por ID inexistente.
 * - Buscar un usuario que no está registrado.
 * - Intentar acceder a un préstamo que no existe.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructor que recibe el mensaje descriptivo del error.
     *
     * @param message Mensaje detallando la causa de la excepción.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

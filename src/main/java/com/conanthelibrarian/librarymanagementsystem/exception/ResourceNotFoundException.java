package com.conanthelibrarian.librarymanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción que se lanza cuando un recurso solicitado no existe en base de datos.
 *
 * <p>Ejemplos típicos:</p>
 * <ul>
 *     <li>Buscar un libro por ID y no encontrarlo</li>
 *     <li>Buscar un usuario por ID y no encontrarlo</li>
 *     <li>Buscar un préstamo por ID y no encontrarlo</li>
 * </ul>
 *
 * <p>Gracias a {@link ResponseStatus}, cuando esta excepción se lanza desde un
 * controlador (o desde un service llamado por un controlador), Spring responderá
 * automáticamente con un HTTP 404 (Not Found).</p>
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructor con mensaje personalizado.
     *
     * @param message mensaje de error
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

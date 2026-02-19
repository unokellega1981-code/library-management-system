package com.conanthelibrarian.librarymanagementsystem.exception.handler;

import com.conanthelibrarian.librarymanagementsystem.exception.BadRequestException;
import com.conanthelibrarian.librarymanagementsystem.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones de la aplicación.
 *
 * <p>Esta clase intercepta excepciones lanzadas desde cualquier Controller
 * y devuelve respuestas HTTP limpias y coherentes.</p>
 *
 * <p>Gracias a esto:</p>
 * <ul>
 *     <li>No devolvemos stacktraces al cliente</li>
 *     <li>Devolvemos códigos HTTP correctos</li>
 *     <li>Unificamos el formato de errores</li>
 * </ul>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja errores cuando un recurso no existe.
     *
     * @param ex excepción lanzada
     * @return respuesta 404 con detalles del error
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", 404);
        response.put("error", "NO_ENCONTRADO");
        response.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Maneja errores de tipo BadRequest (reglas de negocio).
     *
     * @param ex excepción lanzada
     * @return respuesta 400 con detalles del error
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(BadRequestException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", 400);
        response.put("error", "PETICION_INCORRECTA");
        response.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Maneja errores de validación cuando falla un @Valid.
     *
     * <p>Por ejemplo:</p>
     * <ul>
     *     <li>title vacío</li>
     *     <li>availableCopies menor que 1</li>
     *     <li>email inválido</li>
     * </ul>
     *
     * @param ex excepción de validación
     * @return respuesta 400 con un mapa campo -> error
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {

        Map<String, Object> fieldErrors = new HashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", 400);
        response.put("error", "ERROR_DE_VALIDACION");
        response.put("messages", fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }

    /**
     * Maneja cualquier error inesperado no controlado.
     *
     * <p>Esto evita que el cliente reciba stacktraces internos.</p>
     *
     * @param ex excepción general
     * @return respuesta 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", 500);
        response.put("error", "ERROR_INTERNO_DEL_SERVIDOR");
        response.put("message", "Ha ocurrido un error inesperado");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

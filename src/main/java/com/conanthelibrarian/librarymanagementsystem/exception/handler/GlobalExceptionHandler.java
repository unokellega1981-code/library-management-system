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
 * Manejador global de excepciones para toda la aplicación.
 * <p>
 * Esta clase centraliza la gestión de errores lanzados por los
 * controladores REST, permitiendo devolver respuestas JSON
 * estructuradas y coherentes.
 * <p>
 * Evita duplicar lógica de manejo de errores en cada controlador
 * y garantiza uniformidad en las respuestas HTTP.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones del tipo {@link ResourceNotFoundException}.
     * <p>
     * Se lanza cuando un recurso solicitado no existe en la base de datos.
     *
     * @param ex Excepción lanzada.
     * @return Respuesta HTTP 404 con información detallada del error.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", "NO ENCONTRADO");
        response.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Maneja excepciones del tipo {@link BadRequestException}.
     * <p>
     * Se lanza cuando la petición del cliente es inválida o contiene
     * datos incorrectos desde el punto de vista lógico.
     *
     * @param ex Excepción lanzada.
     * @return Respuesta HTTP 400 con detalles del error.
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(BadRequestException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "PETICION INCORRECTA");
        response.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Maneja errores de validación generados por {@link jakarta.validation.Valid}.
     * <p>
     * Se activa cuando un DTO no cumple las restricciones definidas
     * mediante anotaciones como {@code @NotNull}, {@code @NotBlank}, etc.
     *
     * @param ex Excepción lanzada por Spring al fallar la validación.
     * @return Respuesta HTTP 400 con el detalle de cada campo inválido.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {

        Map<String, Object> fieldErrors = new HashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "ERROR DE VALIDACION");
        response.put("messages", fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Maneja cualquier excepción no controlada explícitamente.
     * <p>
     * Actúa como mecanismo de seguridad para evitar que errores internos
     * expongan información sensible al cliente.
     *
     * @param ex Excepción inesperada.
     * @return Respuesta HTTP 500 con mensaje genérico.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", "ERROR INTERNO DEL SERVIDOR");
        response.put("message", "Ha ocurrido un error inesperado");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

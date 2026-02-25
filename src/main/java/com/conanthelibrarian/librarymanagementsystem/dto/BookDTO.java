package com.conanthelibrarian.librarymanagementsystem.dto;

import com.conanthelibrarian.librarymanagementsystem.constants.Genre;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO (Data Transfer Object) para representar un libro.
 *
 * <p>
 * Este objeto se utiliza para transferir datos entre la API y
 * el cliente sin exponer directamente la entidad Book.
 * Incluye validaciones de los campos para asegurar datos correctos.
 * </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookDTO {

    /**
     * Identificador único del libro
     */
    private Integer id;

    /**
     * Título del libro (no puede estar vacío)
     */
    @NotBlank(message = "Tienes que poner un título al libro")
    private String title;

    /**
     * Autor del libro (no puede estar vacío)
     */
    @NotBlank(message = "Tienes que poner un autor al libro")
    private String author;

    /**
     * Código ISBN único del libro (no puede estar vacío)
     */
    @NotBlank(message = "Tienes que poner un ISBN al libro")
    private String isbn;

    /**
     * Género del libro (no puede ser nulo)
     */
    @NotNull(message = "Tienes que poner el género del libro")
    private Genre genre;

    /**
     * Número de copias disponibles (no nulo y >= 0)
     */
    @NotNull(message = "Tienes que poner cuantas copias hay del libro")
    @Min(value = 0, message = "La cantidad de copias no puede ser negativa")
    private Integer availableCopies;
}

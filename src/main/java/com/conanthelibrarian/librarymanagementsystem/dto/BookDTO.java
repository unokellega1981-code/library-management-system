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
 * DTO (Data Transfer Object) que representa un libro en la capa de API.
 *
 * <p>Se utiliza para enviar y recibir información relacionada con libros desde los
 * controladores, evitando exponer directamente la entidad {@code Book}.</p>
 *
 * <p>Este DTO incluye validaciones para garantizar que los datos recibidos en peticiones
 * HTTP sean correctos antes de llegar a la lógica de negocio.</p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookDTO {

    /**
     * Identificador del libro.
     *
     * <p>En operaciones de creación normalmente será {@code null}.
     * En operaciones de actualización sí puede venir informado.</p>
     */
    private Integer id;

    /**
     * Título del libro.
     */
    @NotBlank(message = "Tienes que poner un título al libro")
    private String title;

    /**
     * Autor del libro.
     */
    @NotBlank(message = "Tienes que poner un autor al libro")
    private String author;

    /**
     * ISBN del libro.
     */
    @NotBlank(message = "Tienes que poner un ISBN al libro")
    private String isbn;

    /**
     * Género del libro.
     */
    @NotNull(message = "Tienes que poner el género del libro")
    private Genre genre;

    /**
     * Número de copias disponibles del libro.
     *
     * <p>No puede ser negativo.</p>
     */
    @NotNull(message = "Tienes que poner cuantas copias hay del libro")
    @Min(value = 0, message = "La cantidad de copias no puede ser negativa")
    private Integer availableCopies;

}

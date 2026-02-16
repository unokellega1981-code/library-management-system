package com.conanthelibrarian.librarymanagementsystem.entity;

import com.conanthelibrarian.librarymanagementsystem.constants.Genre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad que representa la tabla "Books" de la base de datos.
 *
 * <p>Contiene la información principal de un libro dentro del sistema,
 * incluyendo su stock disponible para préstamos.</p>
 *
 * <p>Importante: el campo {@code genre} se mapea usando un enum {@link Genre}.
 * Como la base de datos guarda valores con espacios (por ejemplo "Science Fiction"),
 * este campo requiere un {@code AttributeConverter} (GenreConverter) para convertir
 * entre el enum y el texto de la base de datos.</p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Books")
public class Book {

    /**
     * Identificador único del libro.
     * Se genera automáticamente en base de datos con identity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Título del libro.
     */
    @Column(name = "title")
    private String title;

    /**
     * Autor del libro.
     */
    @Column(name = "author")
    private String author;

    /**
     * ISBN del libro.
     */
    @Column(name = "isbn")
    private String isbn;

    /**
     * Género del libro.
     *
     * <p>Se guarda como texto en la base de datos (por ejemplo "Fantasy" o "Science Fiction"),
     * pero en Java se maneja como enum para tener consistencia y evitar errores.</p>
     */
    @Column(name = "genre")
    private Genre genre;

    /**
     * Número de copias disponibles actualmente para préstamo.
     */
    @Column(name = "availableCopies")
    private Integer availableCopies;
}

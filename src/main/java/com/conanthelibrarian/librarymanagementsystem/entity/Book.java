package com.conanthelibrarian.librarymanagementsystem.entity;

import com.conanthelibrarian.librarymanagementsystem.constants.Genre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad que representa un libro dentro del sistema.
 *
 * <p>
 * Está mapeada a la tabla "books" de la base de datos.
 * Contiene información básica como título, autor, ISBN,
 * género y número de copias disponibles.
 * </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "books")
public class Book {

    /**
     * Identificador único del libro.
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
     * Código ISBN único del libro.
     */
    @Column(name = "isbn", unique = true)
    private String isbn;

    /**
     * Género literario del libro.
     * Se convierte automáticamente mediante GenreConverter.
     */
    @Column(name = "genre")
    private Genre genre;

    /**
     * Número de copias disponibles para préstamo.
     */
    @Column(name = "available_copies")
    private Integer availableCopies;
}

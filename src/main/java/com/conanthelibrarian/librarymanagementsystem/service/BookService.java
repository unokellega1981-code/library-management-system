package com.conanthelibrarian.librarymanagementsystem.service;

import com.conanthelibrarian.librarymanagementsystem.constants.Genre;
import com.conanthelibrarian.librarymanagementsystem.dto.BookDTO;

import java.util.List;

/**
 * Servicio que define las operaciones de negocio relacionadas con libros.
 *
 * <p>Esta interfaz representa la capa intermedia entre los controllers (API)
 * y los repositories (persistencia).</p>
 */
public interface BookService {

    /**
     * Obtiene todos los libros disponibles en el sistema.
     *
     * @return lista de libros en formato DTO
     */
    List<BookDTO> getAllBooks();

    /**
     * Obtiene un libro por su identificador.
     *
     * @param id identificador del libro
     * @return libro en formato DTO
     */
    BookDTO getBookById(Integer id);

    /**
     * Crea un nuevo libro.
     *
     * @param bookDTO datos del libro a crear
     * @return libro creado en formato DTO
     */
    BookDTO createBook(BookDTO bookDTO);

    /**
     * Actualiza un libro existente.
     *
     * @param id      identificador del libro a actualizar
     * @param bookDTO datos nuevos del libro
     * @return libro actualizado en formato DTO
     */
    BookDTO updateBook(Integer id, BookDTO bookDTO);

    /**
     * Elimina un libro por su identificador.
     *
     * @param id identificador del libro
     */
    void deleteBook(Integer id);

    /**
     * Obtiene todos los libros de un género específico.
     *
     * @param genre género a filtrar
     * @return lista de libros en formato DTO
     */
    List<BookDTO> getBooksByGenre(Genre genre);

}

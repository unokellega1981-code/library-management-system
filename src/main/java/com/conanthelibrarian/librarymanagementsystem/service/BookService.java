package com.conanthelibrarian.librarymanagementsystem.service;

import com.conanthelibrarian.librarymanagementsystem.dto.BookDTO;

import java.util.List;

/**
 * Servicio para la gestión de libros.
 *
 * <p>
 * Define las operaciones de negocio relacionadas con los libros
 * del sistema. Trabaja exclusivamente con DTOs para evitar exponer
 * directamente las entidades JPA.
 * </p>
 *
 * <p>
 * Las implementaciones de esta interfaz deben encargarse de:
 * <ul>
 *     <li>Interactuar con el repositorio</li>
 *     <li>Aplicar reglas de negocio</li>
 *     <li>Convertir entre entidad y DTO mediante mappers</li>
 * </ul>
 * </p>
 */
public interface BookService {

    /**
     * Obtiene todos los libros del sistema.
     *
     * @return lista de libros en formato DTO
     */
    List<BookDTO> getAllBooks();

    /**
     * Obtiene un libro por su identificador.
     *
     * @param id identificador del libro
     * @return libro encontrado en formato DTO
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
     * @param bookDTO nuevos datos del libro
     * @return libro actualizado en formato DTO
     */
    BookDTO updateBook(Integer id, BookDTO bookDTO);

    /**
     * Elimina un libro por su identificador.
     *
     * @param id identificador del libro a eliminar
     */
    void deleteBook(Integer id);
}

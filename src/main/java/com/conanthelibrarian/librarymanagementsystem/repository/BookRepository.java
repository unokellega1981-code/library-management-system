package com.conanthelibrarian.librarymanagementsystem.repository;

import com.conanthelibrarian.librarymanagementsystem.constants.Genre;
import com.conanthelibrarian.librarymanagementsystem.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio JPA para la entidad Book.
 *
 * <p>
 * Proporciona métodos para realizar operaciones CRUD,
 * búsquedas y paginación de libros en la base de datos.
 * </p>
 *
 * <p>
 * Al extender JpaRepository, ya dispone de métodos como:
 * <ul>
 *     <li>save()</li>
 *     <li>findById()</li>
 *     <li>findAll()</li>
 *     <li>delete()</li>
 * </ul>
 * </p>
 *
 * <p>
 * Además, permite realizar búsquedas personalizadas
 * utilizando el nombre de los métodos.
 * </p>
 */
public interface BookRepository extends JpaRepository<Book, Integer> {

    /**
     * Recupera todos los libros que pertenecen a un género específico.
     *
     * @param genre género del libro
     * @return lista de libros del género indicado
     */
    List<Book> findByGenre(Genre genre);
}

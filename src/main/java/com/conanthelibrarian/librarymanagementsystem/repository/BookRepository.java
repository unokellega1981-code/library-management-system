package com.conanthelibrarian.librarymanagementsystem.repository;

import com.conanthelibrarian.librarymanagementsystem.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

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
 */
public interface BookRepository extends JpaRepository<Book, Integer> {
}

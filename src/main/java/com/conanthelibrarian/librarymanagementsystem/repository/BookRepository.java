package com.conanthelibrarian.librarymanagementsystem.repository;

import com.conanthelibrarian.librarymanagementsystem.constants.Genre;
import com.conanthelibrarian.librarymanagementsystem.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio de acceso a datos para la entidad {@link Book}.
 *
 * <p>Esta interfaz extiende {@link JpaRepository}, lo cual proporciona automáticamente
 * operaciones CRUD básicas sin necesidad de implementar nada:</p>
 *
 * <ul>
 *     <li>Guardar libros ({@code save})</li>
 *     <li>Buscar por ID ({@code findById})</li>
 *     <li>Listar todos ({@code findAll})</li>
 *     <li>Eliminar ({@code deleteById})</li>
 * </ul>
 */
public interface BookRepository extends JpaRepository<Book, Integer> {

    /**
     * Recupera los libros de un género específico.
     *
     * @param genre género del libro
     * @return lista de libros de ese género
     */
    List<Book> findByGenre(Genre genre);
}

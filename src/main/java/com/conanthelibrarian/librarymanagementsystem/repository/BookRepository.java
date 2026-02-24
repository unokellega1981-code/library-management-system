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
     * Recupera todos los libros que pertenecen a un género específico.
     *
     * <p>Este método utiliza la convención de nombres de Spring Data JPA
     * para generar automáticamente la consulta:</p>
     * <pre>
     * SELECT * FROM book WHERE genre = :genre
     * </pre>
     *
     * @param genre género de los libros que se desea buscar (no debe ser null)
     * @return lista de {@link Book} que pertenecen al género indicado.
     *         Devuelve una lista vacía si no se encuentran coincidencias.
     */
    List<Book> findByGenre(Genre genre);
}

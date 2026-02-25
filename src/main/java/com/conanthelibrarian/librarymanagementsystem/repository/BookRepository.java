package com.conanthelibrarian.librarymanagementsystem.repository;

import com.conanthelibrarian.librarymanagementsystem.constants.Genre;
import com.conanthelibrarian.librarymanagementsystem.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

    /**
     * Recupera todos los libros que actualmente están en préstamo.
     *
     * <p>Un libro se considera en préstamo si existe al menos un registro
     * en la entidad Loan asociado a él cuya fecha de devolución (dueDate)
     * sea null.</p>
     *
     * <p>La consulta utiliza JPQL para relacionar Book con Loan.</p>
     *
     * @return lista de {@link Book} que tienen un préstamo activo
     */
    @Query("""
       SELECT DISTINCT b
       FROM Book b
       JOIN Loan l ON l.book = b
       WHERE l.dueDate IS NULL
       """)
    List<Book> findBooksCurrentlyOnLoan();
}

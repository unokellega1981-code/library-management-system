package com.conanthelibrarian.librarymanagementsystem.repository;

import com.conanthelibrarian.librarymanagementsystem.entity.Loan;
import com.conanthelibrarian.librarymanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio JPA para la entidad Loan (préstamos).
 *
 * <p>
 * Permite realizar operaciones CRUD sobre los préstamos de libros,
 * así como búsquedas personalizadas si se agregan métodos adicionales.
 * </p>
 *
 * <p>
 * Métodos por defecto:
 * <ul>
 *     <li>save()</li>
 *     <li>findById()</li>
 *     <li>findAll()</li>
 *     <li>delete()</li>
 * </ul>
 * </p>
 */
public interface LoanRepository extends JpaRepository<Loan, Integer> {

    /**
     * Recupera todos los préstamos que todavía no han sido devueltos.
     *
     * @return lista de préstamos activos (returnedDate == null)
     */
    List<Loan> findBookByReturnedDateIsNull();

    // Esta búsqueda la hago porque la pides pero como he generado la base de datos con la orden de que solo puedes tener
    // un préstamo pendiente a la vez, lo máximo será 1.

    /**
     * Recupera los usuarios que tienen más de X préstamos activos.
     *
     * <p>
     * Un préstamo activo es aquel cuya returnedDate es null.
     * Se agrupa por usuario y se filtra usando HAVING COUNT > :x.
     * </p>
     *
     * @param x número mínimo de préstamos activos
     * @return lista de usuarios que superan ese número
     */
    @Query("""
            SELECT l.user
            FROM Loan l
            WHERE l.returnedDate IS NULL
            GROUP BY l.user
            HAVING COUNT(l) >= :x
            """)
    List<User> findUsersWithMoreThanXActiveLoans(@Param("x") int x);

    // Esta búsqueda no la pides pero la hago porque me parece más natural ya que mira cuantos clientes tienen más de X
    // préstamos en general, tanto devueltos como activos.

    /**
     * Recupera los usuarios que han realizado más de X préstamos
     * a lo largo del tiempo (activos y devueltos).
     *
     * @param x número mínimo de préstamos totales
     * @return lista de usuarios que superan ese número
     */
    @Query("""
            SELECT l.user
            FROM Loan l
            GROUP BY l.user
            HAVING COUNT(l) >= :x
            """)
    List<User> findUsersWithMoreThanXTotalLoans(@Param("x") int x);

    /**
     * Obtiene todos los préstamos asociados a un usuario.
     *
     * <p>
     * Incluye tanto los préstamos activos como aquellos
     * que ya han sido devueltos.
     * </p>
     *
     * @param userId ID del usuario
     * @return lista de préstamos del usuario
     */
    List<Loan> findByUserId(Integer userId);
}

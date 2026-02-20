package com.conanthelibrarian.librarymanagementsystem.repository;

import com.conanthelibrarian.librarymanagementsystem.entity.Book;
import com.conanthelibrarian.librarymanagementsystem.entity.Loan;
import com.conanthelibrarian.librarymanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio de acceso a datos para la entidad {@link Loan}.
 *
 * <p>Esta interfaz proporciona acceso a la tabla {@code Loans} de la base de datos
 * y permite realizar operaciones CRUD sobre préstamos.</p>
 *
 * <p>Gracias a {@link JpaRepository}, se incluyen métodos como:</p>
 * <ul>
 *     <li>{@code save} para crear/actualizar préstamos</li>
 *     <li>{@code findById} para buscar préstamos por ID</li>
 *     <li>{@code findAll} para listar préstamos</li>
 *     <li>{@code deleteById} para eliminar préstamos</li>
 * </ul>
 */
public interface LoanRepository extends JpaRepository<Loan, Integer> {

    /**
     * Recupera todos los libros que están actualmente en préstamo.
     *
     * <p>Un libro se considera actualmente en préstamo si existe al menos
     * un registro en la tabla {@code Loan} asociado a él.</p>
     *
     * <p>Se utiliza {@code DISTINCT} para evitar duplicados en caso de que
     * un mismo libro tenga múltiples préstamos.</p>
     *
     * @return lista de libros actualmente prestados
     */
    @Query("""
           SELECT DISTINCT l.book
           FROM Loan l
           """)
    List<Book> findBooksCurrentlyOnLoan();

    /**
     * Recupera todos los usuarios que tienen más de X préstamos registrados.
     *
     * <p>Se utiliza {@code GROUP BY} junto con {@code HAVING COUNT}
     * para filtrar usuarios cuyo número de préstamos supera el valor indicado.</p>
     *
     * @param minLoans número mínimo de préstamos
     * @return lista de usuarios que superan ese número de préstamos
     */
    @Query("""
           SELECT l.user
           FROM Loan l
           GROUP BY l.user
           HAVING COUNT(l) > :minLoans
           """)
    List<User> findUsersWithMoreThanXLoans(@Param("minLoans") Long minLoans);
}

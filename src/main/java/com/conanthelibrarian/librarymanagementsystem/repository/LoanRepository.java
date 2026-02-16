package com.conanthelibrarian.librarymanagementsystem.repository;

import com.conanthelibrarian.librarymanagementsystem.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

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
}

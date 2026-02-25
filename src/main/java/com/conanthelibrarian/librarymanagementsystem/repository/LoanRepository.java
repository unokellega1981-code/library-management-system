package com.conanthelibrarian.librarymanagementsystem.repository;

import com.conanthelibrarian.librarymanagementsystem.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

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
}

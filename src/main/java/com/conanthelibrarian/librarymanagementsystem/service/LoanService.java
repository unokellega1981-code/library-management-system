package com.conanthelibrarian.librarymanagementsystem.service;

import com.conanthelibrarian.librarymanagementsystem.dto.BookDTO;
import com.conanthelibrarian.librarymanagementsystem.dto.LoanDTO;
import com.conanthelibrarian.librarymanagementsystem.dto.UserDTO;

import java.util.List;

/**
 * Servicio que define las operaciones de negocio relacionadas con préstamos.
 *
 * <p>Un préstamo relaciona un usuario con un libro, e incluye fechas
 * de préstamo y de devolución prevista.</p>
 */
public interface LoanService {

    /**
     * Obtiene todos los préstamos registrados.
     *
     * @return lista de préstamos en formato DTO
     */
    List<LoanDTO> getAllLoans();

    /**
     * Obtiene un préstamo por su identificador.
     *
     * @param id identificador del préstamo
     * @return préstamo en formato DTO
     */
    LoanDTO getLoanById(Integer id);

    /**
     * Crea un nuevo préstamo.
     *
     * <p>IMPORTANTE: aquí normalmente se aplican reglas extra, por ejemplo:</p>
     * <ul>
     *     <li>El libro debe tener copias disponibles</li>
     *     <li>Se debe reducir availableCopies en 1</li>
     * </ul>
     *
     * @param loanDTO datos del préstamo a crear
     * @return préstamo creado
     */
    LoanDTO createLoan(LoanDTO loanDTO);

    /**
     * Actualiza un préstamo existente.
     *
     * @param id identificador del préstamo
     * @param loanDTO datos nuevos del préstamo
     * @return préstamo actualizado en formato DTO
     */
    LoanDTO updateLoan(Integer id, LoanDTO loanDTO);

    /**
     * Elimina un préstamo por su identificador.
     *
     * <p>Normalmente equivale a "devolver" un libro.</p>
     *
     * @param id identificador del préstamo
     */
    void deleteLoan(Integer id);

    /**
     * Obtiene todos los libros que están actualmente en préstamo.
     *
     * @return lista de libros en formato DTO
     */
    List<BookDTO> getBooksCurrentlyOnLoan();

    /**
     * Obtiene todos los usuarios que tienen más de X préstamos.
     *
     * @param minLoans número mínimo de préstamos
     * @return lista de usuarios en formato DTO
     */
    List<UserDTO> getUsersWithMoreThanXLoans(Long minLoans);
}

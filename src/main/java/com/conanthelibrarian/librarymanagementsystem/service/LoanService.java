package com.conanthelibrarian.librarymanagementsystem.service;

import com.conanthelibrarian.librarymanagementsystem.dto.LoanDTO;

import java.util.List;

/**
 * Servicio para la gestión de préstamos.
 *
 * <p>
 * Define las operaciones relacionadas con los préstamos de libros.
 * La implementación debe incluir validaciones como:
 * <ul>
 *     <li>Comprobar que el usuario existe</li>
 *     <li>Comprobar que el libro existe</li>
 *     <li>Verificar disponibilidad de copias</li>
 *     <li>Actualizar copias disponibles</li>
 * </ul>
 * </p>
 */
public interface LoanService {

    /**
     * Obtiene todos los préstamos.
     *
     * @return lista de préstamos en formato DTO
     */
    List<LoanDTO> getAllLoans();

    /**
     * Obtiene un préstamo por su ID.
     *
     * @param id identificador del préstamo
     * @return préstamo encontrado en formato DTO
     */
    LoanDTO getLoanById(Integer id);

    /**
     * Crea un nuevo préstamo.
     *
     * @param loanDTO datos del préstamo
     * @return préstamo creado en formato DTO
     */
    LoanDTO createLoan(LoanDTO loanDTO);

    /**
     * Actualiza un préstamo existente.
     *
     * @param id      identificador del préstamo
     * @param loanDTO nuevos datos
     * @return préstamo actualizado
     */
    LoanDTO updateLoan(Integer id, LoanDTO loanDTO);

    /**
     * Elimina un préstamo.
     *
     * @param id identificador del préstamo
     */
    void deleteLoan(Integer id);

    /**
     * Crea un préstamo rápido a partir del ID del libro y del ID del usuario.
     *
     * <p>
     * Esta operación:
     * <ul>
     *     <li>Verifica que el usuario exista.</li>
     *     <li>Verifica que el libro exista.</li>
     *     <li>Comprueba que haya copias disponibles.</li>
     *     <li>Asigna automáticamente la fecha actual como loanDate.</li>
     *     <li>Calcula automáticamente dueDate sumando 7 días.</li>
     *     <li>Inicializa returnedDate como null.</li>
     * </ul>
     * </p>
     *
     * @param bookId ID del libro a prestar
     * @param userId ID del usuario que recibe el libro
     * @return préstamo creado en formato DTO
     */
    LoanDTO lendBookToUser(Integer bookId, Integer userId);
}

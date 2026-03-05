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

    /**
     * Procesa la devolución de un préstamo.
     *
     * <p>
     * Esta operación realiza las siguientes acciones:
     * <ul>
     *     <li>Verifica que el préstamo exista.</li>
     *     <li>Comprueba que no haya sido devuelto previamente.</li>
     *     <li>Asigna la fecha actual como returnedDate.</li>
     *     <li>Calcula automáticamente el precio según la política de devolución:</li>
     * </ul>
     *
     * <ul>
     *     <li>Si se devuelve antes del dueDate → 10€ menos 1€ por cada día de adelanto.</li>
     *     <li>Si se devuelve el mismo día del dueDate → 10€.</li>
     *     <li>Si se devuelve después del dueDate → 10€ más 2€ por cada día de retraso.</li>
     * </ul>
     *
     * <p>
     * Dado que el préstamo tiene una duración fija de 7 días,
     * el precio mínimo posible será 3€ (devolución el mismo día del préstamo).
     * </p>
     *
     * <p>
     * Finalmente, incrementa en 1 las copias disponibles del libro asociado.
     * </p>
     *
     * @param loanId ID del préstamo a devolver
     * @return préstamo actualizado en formato DTO
     */
    LoanDTO returnBook(Integer loanId);
}

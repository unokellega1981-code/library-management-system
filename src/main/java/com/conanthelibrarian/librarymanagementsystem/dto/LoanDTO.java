package com.conanthelibrarian.librarymanagementsystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para representar un préstamo de libro.
 *
 * <p>
 * Este objeto se utiliza para transferir datos entre la API y
 * el cliente, sin exponer directamente la entidad Loan.
 * Incluye validaciones para los campos obligatorios.
 * </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoanDTO {

    /**
     * Identificador único del préstamo
     */
    private Integer id;

    /**
     * ID del usuario que realiza el préstamo (no nulo)
     */
    @NotNull(message = "Tienes que poner el ID del usuario")
    private Integer userId;

    /**
     * ID del libro prestado (no nulo)
     */
    @NotNull(message = "Tienes que poner el ID del libro")
    private Integer bookId;

    /**
     * Fecha de inicio del préstamo (no nula)
     */
    @NotNull(message = "La fecha de préstamo no puede estar vacía")
    private LocalDate loanDate;

    /**
     * Fecha de vencimiento del préstamo.
     * <p>
     * Este campo se calcula automáticamente por el sistema
     * y no debe ser proporcionado por el cliente.
     */
    private LocalDate dueDate;

    /**
     * Fecha en la que se devolvió el libro (puede ser null)
     */
    private LocalDate returnedDate;

    /**
     * Precio asociado al préstamo o penalización
     */
    private BigDecimal price;
}

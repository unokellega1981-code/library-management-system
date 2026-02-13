package com.conanthelibrarian.librarymanagementsystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) que representa un préstamo en la capa de API.
 *
 * <p>Este DTO se utiliza para enviar y recibir información de préstamos desde los
 * controladores.</p>
 *
 * <p>Importante: a diferencia de la entidad {@code Loan}, que contiene referencias
 * a objetos {@code User} y {@code Book}, este DTO utiliza los IDs
 * ({@code userId} y {@code bookId}).</p>
 *
 * <p>Esto se hace para:</p>
 * <ul>
 *     <li>Simplificar el JSON enviado/recibido</li>
 *     <li>Evitar objetos anidados innecesarios</li>
 *     <li>Evitar problemas de recursión en la serialización</li>
 * </ul>
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoanDTO {

    /**
     * Identificador del préstamo.
     */
    private Integer id;

    /**
     * ID del usuario que recibe el préstamo.
     */
    @NotNull(message = "Tienes que poner el ID del usuario")
    private Integer userId;

    /**
     * ID del libro prestado.
     */
    @NotNull(message = "Tienes que poner el ID del libro")
    private Integer bookId;

    /**
     * Fecha en la que se realizó el préstamo.
     */
    @NotNull(message = "La fecha de préstamo no puede estar vacía")
    private LocalDate loanDate;

    /**
     * Fecha límite en la que el libro debe devolverse.
     */
    @NotNull(message = "La fecha de devolución no puede estar vacía")
    private LocalDate dueDate;

}

package com.conanthelibrarian.librarymanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidad que representa un préstamo de un libro.
 *
 * <p>
 * Relaciona un usuario con un libro y almacena
 * la información temporal del préstamo.
 * </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "loans")
public class Loan {

    /**
     * Identificador único del préstamo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Usuario que realiza el préstamo.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Libro prestado.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    /**
     * Fecha en la que se realiza el préstamo.
     */
    @Column(name = "loan_date")
    private LocalDate loanDate;

    /**
     * Fecha límite de devolución.
     */
    @Column(name = "due_date")
    private LocalDate dueDate;

    /**
     * Fecha real de devolución (puede ser null si no se ha devuelto).
     */
    @Column(name = "returned_date")
    private LocalDate returnedDate;

    /**
     * Precio del préstamo o penalización.
     */
    @Column(name = "price")
    private BigDecimal price;
}

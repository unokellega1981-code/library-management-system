package com.conanthelibrarian.librarymanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Entidad que representa la tabla "Loans" de la base de datos.
 *
 * <p>Un préstamo relaciona un {@link User} con un {@link Book} y contiene
 * la fecha en la que se prestó el libro y la fecha en la que debería devolverse.</p>
 *
 * <p>Importante: en el SQL original la tabla contiene las columnas:</p>
 * <ul>
 *     <li>userId (FK a Users.id)</li>
 *     <li>bookId (FK a Books.id)</li>
 * </ul>
 *
 * <p>En Java, en vez de guardar simplemente los IDs, se usan relaciones
 * {@code @ManyToOne} para trabajar con objetos, lo cual simplifica el código
 * y es la forma estándar con JPA.</p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Loans")
public class Loan {

    /**
     * Identificador único del préstamo.
     * Se genera automáticamente en base de datos con identity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    /**
     * Usuario que tiene el préstamo.
     *
     * <p>Se mapea con la columna userId, que es una FK a Users(id).</p>
     */
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    /**
     * Libro que se ha prestado.
     *
     * <p>Se mapea con la columna bookId, que es una FK a Books(id).</p>
     */
    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;

    /**
     * Fecha en la que se realizó el préstamo.
     *
     * <p>Se usa {@link LocalDate} porque en SQL el tipo es DATE.</p>
     */
    @Column(name = "loanDate")
    private LocalDate loanDate;

    /**
     * Fecha límite para devolver el libro.
     *
     * <p>Se usa {@link LocalDate} porque en SQL el tipo es DATE.</p>
     */
    @Column(name = "dueDate")
    private LocalDate dueDate;
}

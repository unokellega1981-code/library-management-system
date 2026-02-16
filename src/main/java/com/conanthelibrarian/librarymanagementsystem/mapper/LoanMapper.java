package com.conanthelibrarian.librarymanagementsystem.mapper;

import com.conanthelibrarian.librarymanagementsystem.dto.LoanDTO;
import com.conanthelibrarian.librarymanagementsystem.entity.Book;
import com.conanthelibrarian.librarymanagementsystem.entity.Loan;
import com.conanthelibrarian.librarymanagementsystem.entity.User;

/**
 * Clase encargada de convertir entre {@link Loan} (entidad) y {@link LoanDTO} (DTO).
 *
 * <p>Este mapper es ligeramente más complejo que los otros, ya que:</p>
 * <ul>
 *     <li>La entidad {@link Loan} contiene objetos {@link User} y {@link Book}</li>
 *     <li>El DTO {@link LoanDTO} contiene únicamente {@code userId} y {@code bookId}</li>
 * </ul>
 *
 * <p>Esto se hace para simplificar el JSON de entrada/salida en la API.</p>
 */
public class LoanMapper {

    private LoanMapper() {
        // Constructor privado para evitar instanciación.
    }

    /**
     * Convierte una entidad {@link Loan} en un {@link LoanDTO}.
     *
     * <p>Extrae los IDs desde los objetos {@link User} y {@link Book}.</p>
     *
     * @param loan entidad Loan
     * @return DTO equivalente o null si el parámetro es null
     */
    public static LoanDTO toDTO(Loan loan) {
        if (loan == null) {
            return null;
        }

        Integer userId = null;
        if (loan.getUser() != null) {
            userId = loan.getUser().getId();
        }

        Integer bookId = null;
        if (loan.getBook() != null) {
            bookId = loan.getBook().getId();
        }

        return new LoanDTO(
                loan.getId(),
                userId,
                bookId,
                loan.getLoanDate(),
                loan.getDueDate()
        );
    }

    /**
     * Convierte un {@link LoanDTO} en una entidad {@link Loan}.
     *
     * <p>Como el DTO solo contiene IDs, se crean instancias de {@link User} y {@link Book}
     * con únicamente el ID asignado. Esto permite que JPA relacione correctamente
     * las claves foráneas.</p>
     *
     * <p>La validación de que el usuario y el libro existen debe hacerse en la capa Service,
     * no en el mapper.</p>
     *
     * @param loanDTO DTO LoanDTO
     * @return entidad equivalente o null si el parámetro es null
     */
    public static Loan toEntity(LoanDTO loanDTO) {
        if (loanDTO == null) {
            return null;
        }

        Loan loan = new Loan();
        loan.setId(loanDTO.getId());
        loan.setLoanDate(loanDTO.getLoanDate());
        loan.setDueDate(loanDTO.getDueDate());

        if (loanDTO.getUserId() != null) {
            User user = new User();
            user.setId(loanDTO.getUserId());
            loan.setUser(user);
        }

        if (loanDTO.getBookId() != null) {
            Book book = new Book();
            book.setId(loanDTO.getBookId());
            loan.setBook(book);
        }

        return loan;
    }
}

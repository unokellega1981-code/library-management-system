package com.conanthelibrarian.librarymanagementsystem.mapper;

import com.conanthelibrarian.librarymanagementsystem.dto.LoanDTO;
import com.conanthelibrarian.librarymanagementsystem.entity.Book;
import com.conanthelibrarian.librarymanagementsystem.entity.Loan;
import com.conanthelibrarian.librarymanagementsystem.entity.User;

public class LoanMapper {

    /**
     * Constructor privado para evitar instanciación
     */
    private LoanMapper() {
    }

    /**
     * Convierte Loan → LoanDTO
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
                loan.getDueDate(),
                loan.getReturnedDate(),
                loan.getPrice()
        );
    }

    /**
     * Convierte LoanDTO → Loan
     */
    public static Loan toEntity(LoanDTO loanDTO) {
        if (loanDTO == null) {
            return null;
        }

        Loan loan = new Loan();
        loan.setId(loanDTO.getId());
        loan.setLoanDate(loanDTO.getLoanDate());
        loan.setDueDate(loanDTO.getDueDate());
        loan.setReturnedDate(loanDTO.getReturnedDate());
        loan.setPrice(loanDTO.getPrice());

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

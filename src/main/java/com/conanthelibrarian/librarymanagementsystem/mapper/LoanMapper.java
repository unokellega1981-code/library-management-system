package com.conanthelibrarian.librarymanagementsystem.mapper;

import com.conanthelibrarian.librarymanagementsystem.dto.LoanDTO;
import com.conanthelibrarian.librarymanagementsystem.entity.Book;
import com.conanthelibrarian.librarymanagementsystem.entity.Loan;
import com.conanthelibrarian.librarymanagementsystem.entity.User;
import com.conanthelibrarian.librarymanagementsystem.exception.BadRequestException;

public class LoanMapper {

    private LoanMapper() {
    }

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

    public static Loan toEntity(LoanDTO loanDTO) {

        if (loanDTO == null) {
            throw new BadRequestException("El cuerpo de la petición no puede ser null");
        }

        if (loanDTO.getUserId() == null) {
            throw new BadRequestException("El campo 'userId' es obligatorio");
        }

        if (loanDTO.getBookId() == null) {
            throw new BadRequestException("El campo 'bookId' es obligatorio");
        }

        if (loanDTO.getLoanDate() == null) {
            throw new BadRequestException("El campo 'loanDate' es obligatorio");
        }

        if (loanDTO.getDueDate() == null) {
            throw new BadRequestException("El campo 'dueDate' es obligatorio");
        }

        Loan loan = new Loan();
        loan.setId(loanDTO.getId());
        loan.setLoanDate(loanDTO.getLoanDate());
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

package com.conanthelibrarian.librarymanagementsystem.mapper;

import com.conanthelibrarian.librarymanagementsystem.dto.LoanDTO;
import com.conanthelibrarian.librarymanagementsystem.entity.Book;
import com.conanthelibrarian.librarymanagementsystem.entity.Loan;
import com.conanthelibrarian.librarymanagementsystem.entity.User;
import com.conanthelibrarian.librarymanagementsystem.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LoanMapperTest {

    @Test
    void shouldConvertEntityToDTO() {

        // GIVEN
        User user = new User();
        user.setId(1);

        Book book = new Book();
        book.setId(2);

        Loan loan = new Loan();
        loan.setId(10);
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(7));

        // WHEN
        LoanDTO loanDTO = LoanMapper.toDTO(loan);

        // THEN
        assertEquals(loan.getId(), loanDTO.getId());
        assertEquals(user.getId(), loanDTO.getUserId());
        assertEquals(book.getId(), loanDTO.getBookId());
        assertEquals(loan.getLoanDate(), loanDTO.getLoanDate());
        assertEquals(loan.getDueDate(), loanDTO.getDueDate());
    }

    @Test
    void shouldConvertDTOToEntity() {

        // GIVEN
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setId(5);
        loanDTO.setUserId(1);
        loanDTO.setBookId(2);
        loanDTO.setLoanDate(LocalDate.now());
        loanDTO.setDueDate(LocalDate.now().plusDays(7));

        // WHEN
        Loan loan = LoanMapper.toEntity(loanDTO);

        // THEN
        assertEquals(loanDTO.getId(), loan.getId());
        assertEquals(loanDTO.getLoanDate(), loan.getLoanDate());
        assertEquals(loanDTO.getUserId(), loan.getUser().getId());
        assertEquals(loanDTO.getBookId(), loan.getBook().getId());
    }

    @Test
    void shouldReturnNullWhenEntityIsNull() {

        // WHEN
        LoanDTO loanDTO = LoanMapper.toDTO(null);

        // THEN
        assertNull(loanDTO);
    }

    @Test
    void shouldThrowExceptionWhenDTOIsNull() {

        // THEN
        assertThrows(BadRequestException.class, () -> LoanMapper.toEntity(null));
    }
}

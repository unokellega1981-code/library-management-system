package com.conanthelibrarian.librarymanagementsystem.mapper;

import com.conanthelibrarian.librarymanagementsystem.dto.LoanDTO;
import com.conanthelibrarian.librarymanagementsystem.entity.Book;
import com.conanthelibrarian.librarymanagementsystem.entity.Loan;
import com.conanthelibrarian.librarymanagementsystem.entity.User;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para LoanMapper.
 * <p>
 * Comprueba la conversión entre Loan y LoanDTO.
 */
class LoanMapperTest {

    /**
     * Comprueba conversión Entity → DTO.
     */
    @Test
    void shouldConvertEntityToDTO() {

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

        LoanDTO dto = LoanMapper.toDTO(loan);

        assertEquals(loan.getId(), dto.getId());
        assertEquals(user.getId(), dto.getUserId());
        assertEquals(book.getId(), dto.getBookId());
        assertEquals(loan.getLoanDate(), dto.getLoanDate());
        assertEquals(loan.getDueDate(), dto.getDueDate());
    }

    /**
     * Comprueba conversión DTO → Entity.
     */
    @Test
    void shouldConvertDTOToEntity() {

        LoanDTO dto = new LoanDTO();
        dto.setId(5);
        dto.setUserId(1);
        dto.setBookId(2);
        dto.setLoanDate(LocalDate.now());
        dto.setDueDate(LocalDate.now().plusDays(7));

        Loan loan = LoanMapper.toEntity(dto);

        assertEquals(dto.getId(), loan.getId());
        assertEquals(dto.getLoanDate(), loan.getLoanDate());
    }

    /**
     * Comprueba que devuelve null si la entidad es null.
     */
    @Test
    void shouldReturnNullWhenEntityIsNull() {

        LoanDTO dto = LoanMapper.toDTO(null);

        assertNull(dto);
    }

    /**
     * Comprueba que devuelve null si el DTO es null.
     */
    @Test
    void shouldReturnNullWhenDTOIsNull() {

        Loan loan = LoanMapper.toEntity(null);

        assertNull(loan);
    }
}

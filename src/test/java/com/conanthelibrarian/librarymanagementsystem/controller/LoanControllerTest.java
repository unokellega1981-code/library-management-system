package com.conanthelibrarian.librarymanagementsystem.controller;

import com.conanthelibrarian.librarymanagementsystem.dto.BookDTO;
import com.conanthelibrarian.librarymanagementsystem.dto.LoanDTO;
import com.conanthelibrarian.librarymanagementsystem.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanControllerTest {

    @Mock
    private LoanService loanService;

    @InjectMocks
    private LoanController loanController;

    private LoanDTO loanDTO;

    @BeforeEach
    void setUp() {
        loanDTO = new LoanDTO();
        loanDTO.setId(1);
        loanDTO.setUserId(1);
        loanDTO.setBookId(2);
        loanDTO.setLoanDate(LocalDate.now());
        loanDTO.setDueDate(LocalDate.now().plusDays(7));
    }

    @Test
    void shouldReturnAllLoans() {

        // GIVEN
        when(loanService.getAllLoans()).thenReturn(List.of(loanDTO));

        // WHEN
        ResponseEntity<List<LoanDTO>> response = loanController.getAllLoans();

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(loanService).getAllLoans();
    }

    @Test
    void shouldReturnLoanById() {

        // GIVEN
        when(loanService.getLoanById(1)).thenReturn(loanDTO);

        // WHEN
        ResponseEntity<LoanDTO> response = loanController.getLoanById(1);

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getId());
        verify(loanService).getLoanById(1);
    }

    @Test
    void shouldCreateLoan() {

        // GIVEN
        when(loanService.createLoan(any(LoanDTO.class))).thenReturn(loanDTO);

        // WHEN
        ResponseEntity<LoanDTO> response = loanController.createLoan(loanDTO);

        // THEN
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(loanDTO, response.getBody());
        verify(loanService).createLoan(loanDTO);
    }

    @Test
    void shouldDeleteLoan() {

        // GIVEN
        doNothing().when(loanService).deleteLoan(1);

        // WHEN
        ResponseEntity<Void> response = loanController.deleteLoan(1);

        // THEN
        assertEquals(204, response.getStatusCodeValue());
        verify(loanService).deleteLoan(1);
    }

    @Test
    void shouldLendBookToUser() {

        // GIVEN
        when(loanService.lendBookToUser(2, 1)).thenReturn(loanDTO);

        // WHEN
        ResponseEntity<LoanDTO> response =
                loanController.lendBookToUser(2, 1);

        // THEN
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(loanDTO, response.getBody());
        verify(loanService).lendBookToUser(2, 1);
    }

    @Test
    void shouldReturnBook() {

        // GIVEN
        when(loanService.returnBook(1)).thenReturn(loanDTO);

        // WHEN
        ResponseEntity<LoanDTO> response = loanController.returnBook(1);

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(loanDTO, response.getBody());
        verify(loanService).returnBook(1);
    }

    @Test
    void shouldReturnBorrowedBooksByUser() {

        // GIVEN
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(2);

        when(loanService.getBorrowedBooksByUser(1))
                .thenReturn(List.of(bookDTO));

        // WHEN
        ResponseEntity<List<BookDTO>> response =
                loanController.getBorrowedBooksByUser(1);

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(loanService).getBorrowedBooksByUser(1);
    }
}

package com.conanthelibrarian.librarymanagementsystem.service;

import com.conanthelibrarian.librarymanagementsystem.dto.LoanDTO;
import com.conanthelibrarian.librarymanagementsystem.entity.Book;
import com.conanthelibrarian.librarymanagementsystem.entity.Loan;
import com.conanthelibrarian.librarymanagementsystem.entity.User;
import com.conanthelibrarian.librarymanagementsystem.exception.BadRequestException;
import com.conanthelibrarian.librarymanagementsystem.exception.ResourceNotFoundException;
import com.conanthelibrarian.librarymanagementsystem.repository.BookRepository;
import com.conanthelibrarian.librarymanagementsystem.repository.LoanRepository;
import com.conanthelibrarian.librarymanagementsystem.repository.UserRepository;
import com.conanthelibrarian.librarymanagementsystem.service.implementation.LoanServiceImplementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests unitarios para LoanServiceImplementation.
 * <p>
 * Se comprueba toda la lógica de negocio del servicio de préstamos
 * utilizando mocks para los repositorios.
 */
@ExtendWith(MockitoExtension.class)
class LoanServiceImplementationTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private LoanServiceImplementation loanService;

    /**
     * Comprueba que se devuelven todos los préstamos.
     */
    @Test
    void shouldReturnAllLoans() {

        Loan loan1 = new Loan();
        Loan loan2 = new Loan();

        when(loanRepository.findAll()).thenReturn(List.of(loan1, loan2));

        var result = loanService.getAllLoans();

        assertEquals(2, result.size());
        verify(loanRepository).findAll();
    }

    /**
     * Comprueba obtener préstamo por ID.
     */
    @Test
    void shouldReturnLoanById() {

        Loan loan = new Loan();
        loan.setId(1);

        when(loanRepository.findById(1)).thenReturn(Optional.of(loan));

        var result = loanService.getLoanById(1);

        assertNotNull(result);
        verify(loanRepository).findById(1);
    }

    /**
     * Comprueba excepción si el préstamo no existe.
     */
    @Test
    void shouldThrowExceptionIfLoanNotFound() {

        when(loanRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> loanService.getLoanById(1));
    }

    /**
     * Comprueba crear préstamo correctamente.
     */
    @Test
    void shouldCreateLoan() {

        User user = new User();
        user.setId(1);

        Book book = new Book();
        book.setId(1);
        book.setAvailableCopies(5);

        Loan loan = new Loan();
        loan.setLoanDate(LocalDate.now());

        LoanDTO dto = new LoanDTO();
        dto.setUserId(1);
        dto.setBookId(1);
        dto.setLoanDate(LocalDate.now());

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        when(loanRepository.save(any())).thenReturn(loan);

        var result = loanService.createLoan(dto);

        assertNotNull(result);

        verify(bookRepository).save(book);
        verify(loanRepository).save(any());
    }

    /**
     * Comprueba que no se puede crear préstamo sin copias.
     */
    @Test
    void shouldThrowExceptionIfNoCopiesAvailable() {

        User user = new User();
        user.setId(1);

        Book book = new Book();
        book.setId(1);
        book.setAvailableCopies(0);

        LoanDTO dto = new LoanDTO();
        dto.setUserId(1);
        dto.setBookId(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        assertThrows(BadRequestException.class,
                () -> loanService.createLoan(dto));
    }

    /**
     * Comprueba eliminar préstamo devuelto.
     */
    @Test
    void shouldDeleteLoan() {

        Loan loan = new Loan();
        loan.setId(1);
        loan.setReturnedDate(LocalDate.now());

        when(loanRepository.findById(1)).thenReturn(Optional.of(loan));

        loanService.deleteLoan(1);

        verify(loanRepository).delete(loan);
    }

    /**
     * Comprueba que no se puede borrar un préstamo no devuelto.
     */
    @Test
    void shouldThrowExceptionIfLoanNotReturned() {

        Loan loan = new Loan();
        loan.setReturnedDate(null);

        when(loanRepository.findById(1)).thenReturn(Optional.of(loan));

        assertThrows(BadRequestException.class,
                () -> loanService.deleteLoan(1));
    }

    /**
     * Comprueba préstamo rápido (quick loan).
     */
    @Test
    void shouldLendBookToUser() {

        User user = new User();
        user.setId(1);

        Book book = new Book();
        book.setId(1);
        book.setAvailableCopies(2);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        when(loanRepository.save(any())).thenReturn(new Loan());

        var result = loanService.lendBookToUser(1, 1);

        assertNotNull(result);

        verify(bookRepository).save(book);
        verify(loanRepository).save(any());
    }

    /**
     * Comprueba devolución de libro.
     */
    @Test
    void shouldReturnBook() {

        Book book = new Book();
        book.setAvailableCopies(1);

        Loan loan = new Loan();
        loan.setId(1);
        loan.setBook(book);
        loan.setDueDate(LocalDate.now());
        loan.setReturnedDate(null);

        when(loanRepository.findById(1)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any())).thenReturn(loan);

        var result = loanService.returnBook(1);

        assertNotNull(result);
        assertNotNull(loan.getReturnedDate());

        verify(loanRepository).save(loan);
    }

    /**
     * Comprueba que no se puede devolver dos veces.
     */
    @Test
    void shouldThrowExceptionIfAlreadyReturned() {

        Loan loan = new Loan();
        loan.setReturnedDate(LocalDate.now());

        when(loanRepository.findById(1)).thenReturn(Optional.of(loan));

        assertThrows(BadRequestException.class,
                () -> loanService.returnBook(1));
    }

}

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

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoanService loanService;

    @Test
    void shouldReturnAllLoans() {

        Loan loanUno = new Loan();
        Loan loanDos = new Loan();

        when(loanRepository.findAll()).thenReturn(List.of(loanUno, loanDos));

        var result = loanService.getAllLoans();

        assertEquals(2, result.size());
        verify(loanRepository).findAll();
    }

    @Test
    void shouldReturnLoanById() {

        Loan loan = new Loan();
        loan.setId(1);

        when(loanRepository.findById(1)).thenReturn(Optional.of(loan));

        var result = loanService.getLoanById(1);

        assertNotNull(result);
        verify(loanRepository).findById(1);
    }

    @Test
    void shouldThrowExceptionIfLoanNotFound() {

        when(loanRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> loanService.getLoanById(1));
    }

    @Test
    void shouldCreateLoan() {

        User user = new User();
        user.setId(1);

        Book book = new Book();
        book.setId(1);
        book.setAvailableCopies(5);

        Loan loan = new Loan();
        loan.setLoanDate(LocalDate.now());

        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setUserId(1);
        loanDTO.setBookId(1);
        loanDTO.setLoanDate(LocalDate.now());

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        when(loanRepository.save(any())).thenReturn(loan);

        var result = loanService.createLoan(loanDTO);

        assertNotNull(result);

        verify(bookRepository).save(book);
        verify(loanRepository).save(any());
    }

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

    @Test
    void shouldDeleteLoan() {

        Loan loan = new Loan();
        loan.setId(1);
        loan.setReturnedDate(LocalDate.now());

        when(loanRepository.findById(1)).thenReturn(Optional.of(loan));

        loanService.deleteLoan(1);

        verify(loanRepository).delete(loan);
    }

    @Test
    void shouldThrowExceptionIfLoanNotReturned() {

        Loan loan = new Loan();
        loan.setReturnedDate(null);

        when(loanRepository.findById(1)).thenReturn(Optional.of(loan));

        assertThrows(BadRequestException.class,
                () -> loanService.deleteLoan(1));
    }

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

    @Test
    void shouldThrowExceptionIfAlreadyReturned() {

        Loan loan = new Loan();
        loan.setReturnedDate(LocalDate.now());

        when(loanRepository.findById(1)).thenReturn(Optional.of(loan));

        assertThrows(BadRequestException.class,
                () -> loanService.returnBook(1));
    }
}

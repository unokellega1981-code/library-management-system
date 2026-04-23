package com.conanthelibrarian.librarymanagementsystem.service;

import com.conanthelibrarian.librarymanagementsystem.constants.Genre;
import com.conanthelibrarian.librarymanagementsystem.dto.BookDTO;
import com.conanthelibrarian.librarymanagementsystem.entity.Book;
import com.conanthelibrarian.librarymanagementsystem.exception.ResourceNotFoundException;
import com.conanthelibrarian.librarymanagementsystem.repository.BookRepository;
import com.conanthelibrarian.librarymanagementsystem.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void shouldReturnAllBooks() {

        // GIVEN
        Book bookUno = new Book();
        Book bookDos = new Book();

        when(bookRepository.findAll()).thenReturn(List.of(bookUno, bookDos));

        // WHEN
        List<BookDTO> result = bookService.getAllBooks();

        // THEN
        assertEquals(2, result.size());
        verify(bookRepository).findAll();
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void shouldReturnBookById() {

        // GIVEN
        Book book = new Book();
        book.setId(1);

        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        // WHEN
        BookDTO result = bookService.getBookById(1);

        // THEN
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(bookRepository).findById(1);
    }

    @Test
    void shouldThrowExceptionWhenBookNotFound() {

        // GIVEN
        when(bookRepository.findById(1)).thenReturn(Optional.empty());

        // WHEN + THEN
        assertThrows(ResourceNotFoundException.class,
                () -> bookService.getBookById(1));

        verify(bookRepository).findById(1);
    }

    @Test
    void shouldCreateBook() {

        // GIVEN
        BookDTO bookDTO = new BookDTO(1, "Test Book", "Test Author", "123456" , Genre.FANTASY, 3);

        Book book = new Book();
        book.setId(1);
        book.setTitle("Test Book");

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // WHEN
        BookDTO result = bookService.createBook(bookDTO);

        // THEN
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Test Book", result.getTitle());

        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void shouldUpdateBook() {

        // GIVEN
        Book existingBook = new Book();
        existingBook.setId(1);

        BookDTO dto = new BookDTO();
        dto.setTitle("Updated Title");

        when(bookRepository.findById(1)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(existingBook)).thenReturn(existingBook);

        // WHEN
        BookDTO result = bookService.updateBook(1, dto);

        // THEN
        assertNotNull(result);
        verify(bookRepository).findById(1);
        verify(bookRepository).save(existingBook);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingBook() {

        // GIVEN
        when(bookRepository.findById(1)).thenReturn(Optional.empty());

        // WHEN + THEN
        assertThrows(ResourceNotFoundException.class,
                () -> bookService.updateBook(1, new BookDTO()));
    }

    @Test
    void shouldDeleteBook() {

        // GIVEN
        Book book = new Book();
        book.setId(1);

        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        // WHEN
        bookService.deleteBook(1);

        // THEN
        verify(bookRepository).delete(book);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingBook() {

        // GIVEN
        when(bookRepository.findById(1)).thenReturn(Optional.empty());

        // WHEN + THEN
        assertThrows(ResourceNotFoundException.class,
                () -> bookService.deleteBook(1));
    }
}

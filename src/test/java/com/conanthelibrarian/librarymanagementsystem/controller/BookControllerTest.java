package com.conanthelibrarian.librarymanagementsystem.controller;

import com.conanthelibrarian.librarymanagementsystem.constants.Genre;
import com.conanthelibrarian.librarymanagementsystem.dto.BookDTO;
import com.conanthelibrarian.librarymanagementsystem.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private BookDTO bookUno;
    private BookDTO bookDos;

    @BeforeEach
    void setUp() {
        bookUno = new BookDTO(1, "Libro A", "Autor A", "123", Genre.FANTASY, 4);
        bookDos = new BookDTO(2, "Libro B", "Autor B", "456", Genre.SCIENCE_FICTION, 6);
    }

    @Test
    void shouldReturnAllBooks() {

        // GIVEN
        when(bookService.getAllBooks()).thenReturn(List.of(bookUno, bookDos));

        // WHEN
        ResponseEntity<List<BookDTO>> response = bookController.getAllBooks();

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(bookService).getAllBooks();
    }

    @Test
    void shouldReturnBookById() {

        // GIVEN
        when(bookService.getBookById(1)).thenReturn(bookUno);

        // WHEN
        ResponseEntity<BookDTO> response = bookController.getBookById(1);

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Libro A", response.getBody().getTitle());
        verify(bookService).getBookById(1);
    }

    @Test
    void shouldCreateBook() {

        // GIVEN
        when(bookService.createBook(any(BookDTO.class))).thenReturn(bookUno);

        // WHEN
        ResponseEntity<BookDTO> response = bookController.createBook(bookUno);

        // THEN
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(bookUno, response.getBody());
        verify(bookService).createBook(bookUno);
    }

    @Test
    void shouldUpdateBook() {

        // GIVEN
        when(bookService.updateBook(1, bookUno)).thenReturn(bookUno);

        // WHEN
        ResponseEntity<BookDTO> response = bookController.updateBook(1, bookUno);

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(bookUno, response.getBody());
        verify(bookService).updateBook(1, bookUno);
    }

    @Test
    void shouldDeleteBook() {

        // GIVEN
        doNothing().when(bookService).deleteBook(1);

        // WHEN
        ResponseEntity<Void> response = bookController.deleteBook(1);

        // THEN
        assertEquals(204, response.getStatusCodeValue());
        verify(bookService).deleteBook(1);
    }

    @Test
    void shouldReturnBooksByGenre() {

        // GIVEN
        when(bookService.getBooksByGenre(Genre.FANTASY)).thenReturn(List.of(bookUno));

        // WHEN
        ResponseEntity<List<BookDTO>> response = bookController.getBooksByGenre(Genre.FANTASY);

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(bookService).getBooksByGenre(Genre.FANTASY);
    }

    @Test
    void shouldReturnBooksOnLoan() {

        // GIVEN
        when(bookService.getBooksCurrentlyOnLoan()).thenReturn(List.of(bookDos));

        // WHEN
        ResponseEntity<List<BookDTO>> response = bookController.getBooksCurrentlyOnLoan();

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(bookService).getBooksCurrentlyOnLoan();
    }

    @Test
    void shouldReturnBooksByAuthor() {

        // GIVEN
        when(bookService.getBooksByAuthor("Autor A")).thenReturn(List.of(bookUno));

        // WHEN
        ResponseEntity<List<BookDTO>> response = bookController.getBooksByAuthor("Autor A");

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(bookService).getBooksByAuthor("Autor A");
    }
}

package com.conanthelibrarian.librarymanagementsystem.service;

import com.conanthelibrarian.librarymanagementsystem.constants.Genre;
import com.conanthelibrarian.librarymanagementsystem.dto.BookDTO;
import com.conanthelibrarian.librarymanagementsystem.entity.Book;
import com.conanthelibrarian.librarymanagementsystem.entity.Loan;
import com.conanthelibrarian.librarymanagementsystem.exception.ResourceNotFoundException;
import com.conanthelibrarian.librarymanagementsystem.repository.BookRepository;
import com.conanthelibrarian.librarymanagementsystem.repository.LoanRepository;
import com.conanthelibrarian.librarymanagementsystem.service.implementation.BookServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests unitarios para la clase BookServiceImplementation.
 *
 * <p>
 * Se utilizan mocks de los repositorios para aislar la lógica
 * del servicio y comprobar únicamente su comportamiento.
 * </p>
 *
 * <p>
 * Se comprueba:
 * - Obtención de libros
 * - Obtención por ID
 * - Creación de libros
 * - Actualización
 * - Eliminación
 * - Búsqueda por género
 * - Libros actualmente prestados
 * </p>
 */
@ExtendWith(MockitoExtension.class)
class BookServiceImplementationTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private BookServiceImplementation bookService;

    /**
     * Comprueba que el servicio devuelve todos los libros existentes.
     */
    @Test
    void shouldReturnAllBooks() {

        Book book1 = new Book();
        Book book2 = new Book();

        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

        List<BookDTO> result = bookService.getAllBooks();

        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findAll();
    }

    /**
     * Comprueba que se obtiene correctamente un libro por su ID.
     */
    @Test
    void shouldReturnBookById() {

        Book book = new Book();
        book.setId(1);

        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        BookDTO result = bookService.getBookById(1);

        assertNotNull(result);
        verify(bookRepository, times(1)).findById(1);
    }

    /**
     * Comprueba que se lanza una excepción cuando se busca
     * un libro que no existe.
     */
    @Test
    void shouldThrowExceptionWhenBookNotFound() {

        when(bookRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> bookService.getBookById(1));
    }

    /**
     * Comprueba que se crea correctamente un libro.
     */
    @Test
    void shouldCreateBook() {

        BookDTO dto = new BookDTO();
        dto.setTitle("Test Book");
        dto.setAuthor("Author");

        Book book = new Book();
        book.setId(1);

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDTO result = bookService.createBook(dto);

        assertNotNull(result);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    /**
     * Comprueba que se actualiza correctamente un libro existente.
     */
    @Test
    void shouldUpdateBook() {

        Book existingBook = new Book();
        existingBook.setId(1);

        BookDTO dto = new BookDTO();
        dto.setTitle("Updated Title");
        dto.setAuthor("Updated Author");
        dto.setIsbn("123");
        dto.setGenre(Genre.FANTASY);
        dto.setAvailableCopies(5);

        when(bookRepository.findById(1)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenReturn(existingBook);

        BookDTO result = bookService.updateBook(1, dto);

        assertNotNull(result);
        verify(bookRepository).findById(1);
        verify(bookRepository).save(existingBook);
    }

    /**
     * Comprueba que se lanza excepción al intentar actualizar
     * un libro que no existe.
     */
    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingBook() {

        BookDTO dto = new BookDTO();

        when(bookRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> bookService.updateBook(1, dto));
    }

    /**
     * Comprueba que se elimina correctamente un libro.
     */
    @Test
    void shouldDeleteBook() {

        Book book = new Book();
        book.setId(1);

        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        bookService.deleteBook(1);

        verify(bookRepository).delete(book);
    }

    /**
     * Comprueba que se lanza excepción al eliminar
     * un libro que no existe.
     */
    @Test
    void shouldThrowExceptionWhenDeletingNonExistingBook() {

        when(bookRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> bookService.deleteBook(1));
    }

    /**
     * Comprueba que se devuelven correctamente los libros
     * filtrados por género.
     */
    @Test
    void shouldReturnBooksByGenre() {

        Book book = new Book();
        book.setGenre(Genre.FANTASY);

        when(bookRepository.findBookByGenre(Genre.FANTASY))
                .thenReturn(List.of(book));

        List<BookDTO> result = bookService.getBooksByGenre(Genre.FANTASY);

        assertEquals(1, result.size());
        verify(bookRepository).findBookByGenre(Genre.FANTASY);
    }

    /**
     * Comprueba que se devuelven los libros que están actualmente
     * prestados (préstamos sin fecha de devolución).
     */
    @Test
    void shouldReturnBooksCurrentlyOnLoan() {

        Book book = new Book();
        book.setId(1);

        Loan loan = new Loan();
        loan.setBook(book);

        when(loanRepository.findBookByReturnedDateIsNull())
                .thenReturn(List.of(loan));

        List<BookDTO> result = bookService.getBooksCurrentlyOnLoan();

        assertEquals(1, result.size());
        verify(loanRepository).findBookByReturnedDateIsNull();
    }
}

package com.conanthelibrarian.librarymanagementsystem.service.implementation;

import com.conanthelibrarian.librarymanagementsystem.constants.Genre;
import com.conanthelibrarian.librarymanagementsystem.dto.BookDTO;
import com.conanthelibrarian.librarymanagementsystem.entity.Book;
import com.conanthelibrarian.librarymanagementsystem.exception.ResourceNotFoundException;
import com.conanthelibrarian.librarymanagementsystem.mapper.BookMapper;
import com.conanthelibrarian.librarymanagementsystem.repository.BookRepository;
import com.conanthelibrarian.librarymanagementsystem.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio {@link BookService}.
 *
 * <p>Esta clase contiene la lógica de negocio relacionada con libros y actúa como puente
 * entre los controllers (API) y los repositories (persistencia).</p>
 *
 * <p>En esta implementación se utiliza {@link BookRepository} para acceder a base de datos
 * y {@link BookMapper} para convertir entre entidades y DTOs.</p>
 */
@Service
public class BookServiceImplementation implements BookService {

    private final BookRepository bookRepository;

    /**
     * Constructor para inyección de dependencias.
     *
     * <p>Spring inyectará automáticamente el {@link BookRepository} gracias a que
     * esta clase está anotada con {@link Service}.</p>
     *
     * @param bookRepository repositorio de libros
     */
    public BookServiceImplementation(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookMapper::toDTO)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookDTO getBookById(Integer id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No se ha encontrado ningún libro con el ID: " + id));

        return BookMapper.toDTO(book);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        Book book = BookMapper.toEntity(bookDTO);

        // Seguridad extra: el ID debe generarlo la base de datos.
        book.setId(null);

        Book savedBook = bookRepository.save(book);
        return BookMapper.toDTO(savedBook);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookDTO updateBook(Integer id, BookDTO bookDTO) {

        // 1) Comprobar que el libro existe
        Book existingBook = bookRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No se ha encontrado ningún libro con el ID: " + id));

        // 2) Actualizar campos
        existingBook.setTitle(bookDTO.getTitle());
        existingBook.setAuthor(bookDTO.getAuthor());
        existingBook.setIsbn(bookDTO.getIsbn());
        existingBook.setGenre(bookDTO.getGenre());
        existingBook.setAvailableCopies(bookDTO.getAvailableCopies());

        // 3) Guardar cambios
        Book updatedBook = bookRepository.save(existingBook);

        return BookMapper.toDTO(updatedBook);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteBook(Integer id) {

        // Comprobar que existe antes de borrar
        Book existingBook = bookRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No se ha encontrado ningún libro con el ID: " + id));

        bookRepository.delete(existingBook);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BookDTO> getBooksByGenre(Genre genre) {
        return bookRepository.findByGenre(genre)
                .stream()
                .map(BookMapper::toDTO)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BookDTO> getBooksCurrentlyOnLoan() {
        return bookRepository.findBooksCurrentlyOnLoan()
                .stream()
                .map(BookMapper::toDTO)
                .toList();
    }
}

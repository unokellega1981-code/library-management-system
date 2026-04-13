package com.conanthelibrarian.librarymanagementsystem.service.implementation;

import com.conanthelibrarian.librarymanagementsystem.constants.Genre;
import com.conanthelibrarian.librarymanagementsystem.dto.BookDTO;
import com.conanthelibrarian.librarymanagementsystem.entity.Book;
import com.conanthelibrarian.librarymanagementsystem.entity.Loan;
import com.conanthelibrarian.librarymanagementsystem.exception.ResourceNotFoundException;
import com.conanthelibrarian.librarymanagementsystem.mapper.BookMapper;
import com.conanthelibrarian.librarymanagementsystem.repository.BookRepository;
import com.conanthelibrarian.librarymanagementsystem.repository.LoanRepository;
import com.conanthelibrarian.librarymanagementsystem.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BookServiceImplementation implements BookService {

    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    public BookServiceImplementation(BookRepository bookRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        log.info("Recuperando todos los libros");
        return bookRepository.findAll()
                .stream()
                .map(BookMapper::toDTO)
                .toList();
    }

    @Override
    public BookDTO getBookById(Integer id) {
        log.info("Buscando libro con ID {}", id);

        Book book = bookRepository.findById(id).orElseThrow(() -> {
            log.warn("Libro no encontrado con ID {}", id);
            return new ResourceNotFoundException("No se ha encontrado ningún libro con el ID: " + id);
        });

        return BookMapper.toDTO(book);
    }

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        log.info("Creando libro: {}", bookDTO);

        Book book = BookMapper.toEntity(bookDTO);
        book.setId(null);

        Book savedBook = bookRepository.save(book);

        log.info("Libro creado con ID {}", savedBook.getId());

        return BookMapper.toDTO(savedBook);
    }

    @Override
    public BookDTO updateBook(Integer id, BookDTO bookDTO) {
        log.info("Actualizando libro con ID {}", id);

        Book existingBook = bookRepository.findById(id).orElseThrow(() -> {
            log.warn("Intento de actualizar libro inexistente con ID {}", id);
            return new ResourceNotFoundException("No se ha encontrado ningún libro con el ID: " + id);
        });

        existingBook.setTitle(bookDTO.getTitle());
        existingBook.setAuthor(bookDTO.getAuthor());
        existingBook.setIsbn(bookDTO.getIsbn());
        existingBook.setGenre(bookDTO.getGenre());
        existingBook.setAvailableCopies(bookDTO.getAvailableCopies());

        Book updatedBook = bookRepository.save(existingBook);

        log.info("Libro actualizado correctamente con ID {}", id);

        return BookMapper.toDTO(updatedBook);
    }

    @Override
    public void deleteBook(Integer id) {
        log.warn("Eliminando libro con ID {}", id);

        Book existingBook = bookRepository.findById(id).orElseThrow(() -> {
            log.warn("Intento de borrar libro inexistente con ID {}", id);
            return new ResourceNotFoundException("No se ha encontrado ningún libro con el ID: " + id);
        });

        bookRepository.delete(existingBook);

        log.info("Libro eliminado con ID {}", id);
    }

    @Override
    public List<BookDTO> getBooksByGenre(Genre genre) {
        log.info("Buscando libros por género {}", genre);

        return bookRepository.findBookByGenre(genre)
                .stream()
                .map(BookMapper::toDTO)
                .toList();
    }

    @Override
    public List<BookDTO> getBooksCurrentlyOnLoan() {
        log.info("Recuperando libros actualmente prestados");

        return loanRepository.findBookByReturnedDateIsNull()
                .stream()
                .map(Loan::getBook)
                .distinct()
                .map(BookMapper::toDTO)
                .toList();
    }

    @Override
    public List<BookDTO> getBooksByAuthor(String author) {
        log.info("Buscando libros del autor {}", author);

        return bookRepository.findByAuthor(author)
                .stream()
                .map(BookMapper::toDTO)
                .toList();
    }
}

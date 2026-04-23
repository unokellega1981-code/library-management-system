package com.conanthelibrarian.librarymanagementsystem.controller;

import com.conanthelibrarian.librarymanagementsystem.constants.Genre;
import com.conanthelibrarian.librarymanagementsystem.dto.BookDTO;
import com.conanthelibrarian.librarymanagementsystem.mapper.BookMapper;
import com.conanthelibrarian.librarymanagementsystem.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    BookMapper bookMapper = new BookMapper();

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        log.info("GET /api/books - Recuperando todos los libros");
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Integer id) {
        log.info("GET /api/books/{} - Buscando libro por ID", id);
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping("/createbook")
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        log.info("POST /api/books/createbook - Creando libro: {}", bookDTO);
        bookMapper.toEntity(bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookService.createBook(bookDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Integer id, @RequestBody BookDTO bookDTO) {
        log.info("PUT /api/books/{} - Actualizando libro con datos: {}", id, bookDTO);
        bookMapper.toEntity(bookDTO);
        return ResponseEntity.ok(bookService.updateBook(id, bookDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        log.warn("DELETE /api/books/{} - Eliminando libro", id);
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<BookDTO>> getBooksByGenre(@PathVariable Genre genre) {
        log.info("GET /api/books/genre/{} - Filtrando libros por género", genre);
        return ResponseEntity.ok(bookService.getBooksByGenre(genre));
    }

    @GetMapping("/on-loan")
    public ResponseEntity<List<BookDTO>> getBooksCurrentlyOnLoan() {
        log.info("GET /api/books/on-loan - Recuperando libros prestados");
        return ResponseEntity.ok(bookService.getBooksCurrentlyOnLoan());
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<BookDTO>> getBooksByAuthor(@PathVariable String author) {
        log.info("GET /api/books/author/{} - Buscando libros por autor", author);
        return ResponseEntity.ok(bookService.getBooksByAuthor(author));
    }
}

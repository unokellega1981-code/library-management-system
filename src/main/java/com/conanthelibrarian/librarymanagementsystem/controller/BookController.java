package com.conanthelibrarian.librarymanagementsystem.controller;

import com.conanthelibrarian.librarymanagementsystem.constants.Genre;
import com.conanthelibrarian.librarymanagementsystem.dto.BookDTO;
import com.conanthelibrarian.librarymanagementsystem.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST encargado de gestionar las operaciones relacionadas con libros.
 *
 * <p>Este controlador expone endpoints para realizar operaciones CRUD sobre libros.</p>
 *
 * <p>Ruta base: {@code /api/books}</p>
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    /**
     * Constructor para inyección de dependencias.
     *
     * @param bookService servicio de libros
     */
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Obtiene todos los libros.
     *
     * @return lista de libros en formato DTO
     */
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    /**
     * Obtiene un libro por su ID.
     *
     * @param id identificador del libro
     * @return libro encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Integer id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    /**
     * Crea un nuevo libro.
     *
     * <p>Se validan los datos recibidos mediante {@link Valid} y las anotaciones
     * del DTO.</p>
     *
     * @param bookDTO datos del libro
     * @return libro creado
     */
    @PostMapping
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.createBook(bookDTO));
    }

    /**
     * Actualiza un libro existente.
     *
     * @param id identificador del libro
     * @param bookDTO datos nuevos
     * @return libro actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Integer id, @Valid @RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDTO));
    }

    /**
     * Elimina un libro por su ID.
     *
     * @param id identificador del libro
     * @return respuesta sin contenido
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Recupera todos los libros que pertenecen a un género específico.
     *
     * <p>Este endpoint permite filtrar los libros registrados en el sistema
     * según el género indicado en la URL.</p>
     *
     * <p>Si no existen libros para el género especificado,
     * se devuelve una lista vacía con estado 200 OK.</p>
     *
     * @param genre género de los libros que se desea buscar
     * @return lista de libros en formato {@link BookDTO} que pertenecen al género indicado
     */
    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<BookDTO>> getBooksByGenre(@PathVariable Genre genre) {
        return ResponseEntity.ok(bookService.getBooksByGenre(genre));
    }

    /**
     * Recupera todos los libros que actualmente están en préstamo.
     *
     * <p>Un libro se considera en préstamo si tiene al menos un préstamo
     * activo (dueDate == null).</p>
     *
     * @return lista de libros en formato {@link BookDTO}
     */
    @GetMapping("/on-loan")
    public ResponseEntity<List<BookDTO>> getBooksCurrentlyOnLoan() {
        return ResponseEntity.ok(bookService.getBooksCurrentlyOnLoan());
    }
}

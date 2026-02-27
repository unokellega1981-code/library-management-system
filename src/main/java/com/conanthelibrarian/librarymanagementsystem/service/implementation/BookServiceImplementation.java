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
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio de gestión de libros.
 * <p>
 * Esta clase contiene la lógica de negocio relacionada con la entidad Book.
 * Se encarga de interactuar con el repositorio, aplicar validaciones
 * y transformar entidades a DTOs y viceversa mediante el BookMapper.
 * <p>
 * Responsabilidades:
 * - Obtener todos los libros
 * - Obtener libro por ID
 * - Crear libros
 * - Actualizar libros existentes
 * - Eliminar libros
 * <p>
 * Lanza:
 * - ResourceNotFoundException cuando no existe el recurso solicitado
 */
@Service
public class BookServiceImplementation implements BookService {

    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    public BookServiceImplementation(BookRepository bookRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
    }

    /**
     * Obtiene todos los libros registrados en el sistema.
     *
     * @return Lista de libros en formato DTO.
     */
    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookMapper::toDTO)
                .toList();
    }

    /**
     * Obtiene un libro por su identificador.
     *
     * @param id ID del libro a buscar.
     * @return Libro encontrado en formato DTO.
     * @throws ResourceNotFoundException si no existe ningún libro con el ID indicado.
     */
    @Override
    public BookDTO getBookById(Integer id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No se ha encontrado ningún libro con el ID: " + id));

        return BookMapper.toDTO(book);
    }

    /**
     * Crea un nuevo libro en el sistema.
     * El ID se establece automáticamente ignorando cualquier valor recibido.
     *
     * @param bookDTO Datos del libro a crear.
     * @return Libro creado en formato DTO.
     */
    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        Book book = BookMapper.toEntity(bookDTO);

        book.setId(null);

        Book savedBook = bookRepository.save(book);
        return BookMapper.toDTO(savedBook);
    }

    /**
     * Actualiza los datos de un libro existente.
     *
     * @param id      ID del libro a actualizar.
     * @param bookDTO Datos actualizados.
     * @return Libro actualizado en formato DTO.
     * @throws ResourceNotFoundException si el libro no existe.
     */
    @Override
    public BookDTO updateBook(Integer id, BookDTO bookDTO) {

        Book existingBook = bookRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No se ha encontrado ningún libro con el ID: " + id));

        existingBook.setTitle(bookDTO.getTitle());
        existingBook.setAuthor(bookDTO.getAuthor());
        existingBook.setIsbn(bookDTO.getIsbn());
        existingBook.setGenre(bookDTO.getGenre());
        existingBook.setAvailableCopies(bookDTO.getAvailableCopies());

        Book updatedBook = bookRepository.save(existingBook);

        return BookMapper.toDTO(updatedBook);
    }

    /**
     * Elimina un libro del sistema.
     *
     * @param id ID del libro a eliminar.
     * @throws ResourceNotFoundException si el libro no existe.
     */
    @Override
    public void deleteBook(Integer id) {

        Book existingBook = bookRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No se ha encontrado ningún libro con el ID: " + id));

        bookRepository.delete(existingBook);
    }

    /**
     * Obtiene todos los libros que pertenecen a un género específico.
     *
     * @param genre género a filtrar.
     * @return Lista de libros en formato DTO.
     */
    @Override
    public List<BookDTO> getBooksByGenre(Genre genre) {

        return bookRepository.findBookByGenre(genre)
                .stream()
                .map(BookMapper::toDTO)
                .toList();
    }

    /**
     * Obtiene todos los libros que actualmente están en préstamo.
     *
     * <p>
     * Se consideran en préstamo aquellos libros que tienen
     * al menos un registro Loan cuyo returnedDate es null.
     * </p>
     *
     * @return Lista de libros en formato DTO sin duplicados.
     */
    @Override
    public List<BookDTO> getBooksCurrentlyOnLoan() {

        return loanRepository.findBookByReturnedDateIsNull()
                .stream()
                .map(Loan::getBook)
                .distinct() // evita duplicados si hay varios préstamos del mismo libro
                .map(BookMapper::toDTO)
                .toList();
    }
}

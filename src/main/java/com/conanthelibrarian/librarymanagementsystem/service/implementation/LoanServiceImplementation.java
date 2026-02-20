package com.conanthelibrarian.librarymanagementsystem.service.implementation;

import com.conanthelibrarian.librarymanagementsystem.dto.BookDTO;
import com.conanthelibrarian.librarymanagementsystem.dto.LoanDTO;
import com.conanthelibrarian.librarymanagementsystem.dto.UserDTO;
import com.conanthelibrarian.librarymanagementsystem.entity.Book;
import com.conanthelibrarian.librarymanagementsystem.entity.Loan;
import com.conanthelibrarian.librarymanagementsystem.entity.User;
import com.conanthelibrarian.librarymanagementsystem.exception.ResourceNotFoundException;
import com.conanthelibrarian.librarymanagementsystem.exception.BadRequestException;
import com.conanthelibrarian.librarymanagementsystem.mapper.BookMapper;
import com.conanthelibrarian.librarymanagementsystem.mapper.LoanMapper;
import com.conanthelibrarian.librarymanagementsystem.mapper.UserMapper;
import com.conanthelibrarian.librarymanagementsystem.repository.BookRepository;
import com.conanthelibrarian.librarymanagementsystem.repository.LoanRepository;
import com.conanthelibrarian.librarymanagementsystem.repository.UserRepository;
import com.conanthelibrarian.librarymanagementsystem.service.LoanService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio {@link LoanService}.
 *
 * <p>Esta clase contiene la lógica de negocio relacionada con préstamos.</p>
 *
 * <p>A diferencia de Book y User, aquí sí existen reglas extra:</p>
 * <ul>
 *     <li>Un préstamo solo puede crearse si el libro tiene copias disponibles</li>
 *     <li>Al crear un préstamo, se reduce {@code availableCopies} en 1</li>
 *     <li>Al eliminar un préstamo (devolver libro), se incrementa {@code availableCopies} en 1</li>
 * </ul>
 *
 * <p>Estas reglas son necesarias para mantener coherencia entre la tabla Loans
 * y el stock de libros.</p>
 */
@Service
public class LoanServiceImplementation implements LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    /**
     * Constructor para inyección de dependencias.
     *
     * @param loanRepository repositorio de préstamos
     * @param userRepository repositorio de usuarios
     * @param bookRepository repositorio de libros
     */
    public LoanServiceImplementation(LoanRepository loanRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LoanDTO> getAllLoans() {
        return loanRepository.findAll()
                .stream()
                .map(LoanMapper::toDTO)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LoanDTO getLoanById(Integer id) {
        Loan loan = loanRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No se ha encontrado ningún préstamo con el ID: " + id)
        );

        return LoanMapper.toDTO(loan);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Este método está anotado con {@link Transactional} para garantizar que:</p>
     * <ul>
     *     <li>La reducción de copias disponibles</li>
     *     <li>La creación del préstamo</li>
     * </ul>
     *
     * <p>se ejecuten como una sola operación atómica.</p>
     */
    @Transactional
    @Override
    public LoanDTO createLoan(LoanDTO loanDTO) {

        // 1) Comprobar que el usuario existe
        User user = userRepository.findById(loanDTO.getUserId()).orElseThrow(() ->
                new ResourceNotFoundException("No se ha encontrado ningún usuario con el ID: " + loanDTO.getUserId())
        );

        // 2) Comprobar que el libro existe
        Book book = bookRepository.findById(loanDTO.getBookId()).orElseThrow(() ->
                new ResourceNotFoundException("No se ha encontrado ningún libro con el ID: " + loanDTO.getBookId())
        );

        // 3) Comprobar stock disponible
        if (book.getAvailableCopies() == null || book.getAvailableCopies() <= 0) {
            throw new BadRequestException("No hay copias disponibles del libro con ID: " + book.getId());
        }

        // 4) Reducir stock
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        // 5) Crear préstamo
        Loan loan = LoanMapper.toEntity(loanDTO);

        // Seguridad extra: el ID debe generarlo la base de datos
        loan.setId(null);

        // IMPORTANTE:
        // Reemplazamos las referencias creadas por el mapper (solo con ID)
        // por entidades reales cargadas desde base de datos.
        loan.setUser(user);
        loan.setBook(book);

        // 6) Guardar préstamo
        Loan savedLoan = loanRepository.save(loan);

        return LoanMapper.toDTO(savedLoan);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Actualiza un préstamo existente. Como un préstamo relaciona un {@link User} con un {@link Book},
     * esta operación incluye validaciones adicionales:</p>
     *
     * <ul>
     *     <li>Comprueba que el préstamo existe</li>
     *     <li>Comprueba que el usuario existe</li>
     *     <li>Comprueba que el libro existe</li>
     * </ul>
     *
     * <p>Si el préstamo cambia de libro, se ajusta el stock de copias disponibles:</p>
     * <ul>
     *     <li>Se devuelve 1 copia al libro anterior</li>
     *     <li>Se comprueba que el nuevo libro tenga copias disponibles</li>
     *     <li>Se resta 1 copia al nuevo libro</li>
     * </ul>
     *
     * <p>Este método es transaccional para garantizar que la modificación de stock
     * y la actualización del préstamo se realicen como una operación atómica.</p>
     *
     * @param id      identificador del préstamo a actualizar
     * @param loanDTO datos nuevos del préstamo
     * @return préstamo actualizado en formato DTO
     */
    @Transactional
    @Override
    public LoanDTO updateLoan(Integer id, LoanDTO loanDTO) {

        // 1) Comprobar que el préstamo existe
        Loan existingLoan = loanRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No se ha encontrado ningún préstamo con el ID: " + id)
        );

        // 2) Validar que el usuario existe
        User user = userRepository.findById(loanDTO.getUserId()).orElseThrow(() ->
                new ResourceNotFoundException("No se ha encontrado ningún usuario con el ID: " + loanDTO.getUserId())
        );

        // 3) Validar que el libro existe
        Book newBook = bookRepository.findById(loanDTO.getBookId()).orElseThrow(() ->
                new ResourceNotFoundException("No se ha encontrado ningún libro con el ID: " + loanDTO.getBookId())
        );

        // 4) Si se cambia el libro, hay que gestionar stock
        Book oldBook = existingLoan.getBook();

        if (oldBook != null && !oldBook.getId().equals(newBook.getId())) {

            // devolver copia al libro anterior
            Integer oldCopies = oldBook.getAvailableCopies() == null ? 0 : oldBook.getAvailableCopies();
            oldBook.setAvailableCopies(oldCopies + 1);
            bookRepository.save(oldBook);

            // comprobar stock del nuevo libro
            if (newBook.getAvailableCopies() == null || newBook.getAvailableCopies() <= 0) {
                throw new BadRequestException("No hay copias disponibles del libro con ID: " + newBook.getId());
            }

            // quitar copia al nuevo libro
            newBook.setAvailableCopies(newBook.getAvailableCopies() - 1);
            bookRepository.save(newBook);
        }

        // 5) Actualizar campos del préstamo
        existingLoan.setUser(user);
        existingLoan.setBook(newBook);
        existingLoan.setLoanDate(loanDTO.getLoanDate());
        existingLoan.setDueDate(loanDTO.getDueDate());

        // 6) Guardar
        Loan updatedLoan = loanRepository.save(existingLoan);

        return LoanMapper.toDTO(updatedLoan);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Este método está anotado con {@link Transactional} para garantizar que:</p>
     * <ul>
     *     <li>La eliminación del préstamo</li>
     *     <li>La devolución de stock (incremento de copias)</li>
     * </ul>
     *
     * <p>se ejecuten como una sola operación atómica.</p>
     */
    @Transactional
    @Override
    public void deleteLoan(Integer id) {

        // 1) Comprobar que el préstamo existe
        Loan loan = loanRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No se ha encontrado ningún préstamo con el ID: " + id));

        // 2) Recuperar el libro asociado al préstamo
        Book book = loan.getBook();

        // 3) Incrementar copias disponibles (devolución)
        if (book != null) {
            Integer currentCopies = book.getAvailableCopies() == null ? 0 : book.getAvailableCopies();
            book.setAvailableCopies(currentCopies + 1);
            bookRepository.save(book);
        }

        // 4) Eliminar préstamo
        loanRepository.delete(loan);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BookDTO> getBooksCurrentlyOnLoan() {

        return loanRepository.findBooksCurrentlyOnLoan()
                .stream()
                .map(BookMapper::toDTO)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserDTO> getUsersWithMoreThanXLoans(Long minLoans) {

        return loanRepository.findUsersWithMoreThanXLoans(minLoans)
                .stream()
                .map(UserMapper::toDTO)
                .toList();
    }
}

package com.conanthelibrarian.librarymanagementsystem.service.implementation;

import com.conanthelibrarian.librarymanagementsystem.dto.LoanDTO;
import com.conanthelibrarian.librarymanagementsystem.entity.Book;
import com.conanthelibrarian.librarymanagementsystem.entity.Loan;
import com.conanthelibrarian.librarymanagementsystem.entity.User;
import com.conanthelibrarian.librarymanagementsystem.exception.ResourceNotFoundException;
import com.conanthelibrarian.librarymanagementsystem.exception.BadRequestException;
import com.conanthelibrarian.librarymanagementsystem.mapper.LoanMapper;
import com.conanthelibrarian.librarymanagementsystem.repository.BookRepository;
import com.conanthelibrarian.librarymanagementsystem.repository.LoanRepository;
import com.conanthelibrarian.librarymanagementsystem.repository.UserRepository;
import com.conanthelibrarian.librarymanagementsystem.service.LoanService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementación del servicio de gestión de préstamos.
 * <p>
 * Esta clase contiene la lógica de negocio relacionada con la entidad Loan.
 * Gestiona la relación entre usuarios y libros, incluyendo:
 * <p>
 * - Creación de préstamos
 * - Actualización de préstamos
 * - Eliminación de préstamos
 * - Control de disponibilidad de copias
 * <p>
 * Características importantes:
 * <p>
 * 1. Verifica que el usuario y el libro existan antes de crear o actualizar un préstamo.
 * 2. Comprueba que el libro tenga copias disponibles antes de prestarlo.
 * 3. Reduce automáticamente el número de copias disponibles al crear un préstamo.
 * 4. Si se cambia el libro en una actualización, ajusta correctamente las copias:
 * - Devuelve una copia al libro anterior.
 * - Resta una copia al nuevo libro.
 * 5. Cálculo automático de la fecha de vencimiento (dueDate).
 * 6. Solo permite eliminar un préstamo si el libro ha sido devuelto
 * (returnedDate distinto de null).
 * <p>
 * Uso de @Transactional:
 * Se utiliza en los métodos que modifican múltiples entidades
 * (por ejemplo, libro + préstamo) para garantizar consistencia
 * en caso de error.
 * <p>
 * Excepciones lanzadas:
 * - ResourceNotFoundException: cuando no existe usuario, libro o préstamo.
 * - BadRequestException: cuando no hay copias disponibles o
 * cuando se intenta borrar un préstamo no devuelto.
 */
@Service
public class LoanServiceImplementation implements LoanService {

    /**
     * Duración estándar de un préstamo en días.
     * Se define como constante para facilitar futuras modificaciones.
     */
    private static final int LOAN_DURATION_DAYS = 7;

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public LoanServiceImplementation(LoanRepository loanRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * Obtiene todos los préstamos registrados.
     *
     * @return Lista de préstamos en formato DTO.
     */
    @Override
    public List<LoanDTO> getAllLoans() {
        return loanRepository.findAll().
                stream().
                map(LoanMapper::toDTO).
                toList();
    }

    /**
     * Obtiene un préstamo por su identificador.
     *
     * @param id ID del préstamo.
     * @return Préstamo encontrado en formato DTO.
     * @throws ResourceNotFoundException si no existe.
     */
    @Override
    public LoanDTO getLoanById(Integer id) {
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado ningún préstamo con el ID: " + id));
        return LoanMapper.toDTO(loan);
    }

    /**
     * Crea un nuevo préstamo.
     *
     * Reglas aplicadas:
     * - Verifica que el usuario exista.
     * - Verifica que el libro exista.
     * - Comprueba que haya copias disponibles.
     * - Reduce en una unidad las copias disponibles del libro.
     * - Calcula automáticamente la fecha de vencimiento (dueDate)
     *   sumando LOAN_DURATION_DAYS a loanDate.
     *
     * Si el cliente envía un dueDate en el DTO, será ignorado.
     */
    @Transactional
    @Override
    public LoanDTO createLoan(LoanDTO loanDTO) {

        User user = userRepository.findById(loanDTO.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("No se ha encontrado ningún usuario con el ID: " + loanDTO.getUserId()));

        Book book = bookRepository.findById(loanDTO.getBookId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("No se ha encontrado ningún libro con el ID: " + loanDTO.getBookId()));

        if (book.getAvailableCopies() == null || book.getAvailableCopies() <= 0) {
            throw new BadRequestException("No hay copias disponibles del libro con ID: " + book.getId());
        }
        // Reducir copia disponible
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        Loan loan = LoanMapper.toEntity(loanDTO);
        loan.setId(null);
        loan.setUser(user);
        loan.setBook(book);

        // Cálculo automático del dueDate
        loan.setDueDate(loan.getLoanDate().plusDays(LOAN_DURATION_DAYS));

        Loan savedLoan = loanRepository.save(loan);

        return LoanMapper.toDTO(savedLoan);
    }

    /**
     * Actualiza un préstamo existente.
     *
     * Si se cambia el libro:
     * - Devuelve una copia al libro anterior.
     * - Comprueba disponibilidad del nuevo libro.
     * - Reduce en una unidad el nuevo libro.
     *
     * Además:
     * - Si se modifica loanDate, se recalcula automáticamente dueDate.
     */
    @Transactional
    @Override
    public LoanDTO updateLoan(Integer id, LoanDTO loanDTO) {

        Loan existingLoan = loanRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("No se ha encontrado ningún préstamo con el ID: " + id));

        User user = userRepository.findById(loanDTO.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("No se ha encontrado ningún usuario con el ID: " + loanDTO.getUserId()));

        Book newBook = bookRepository.findById(loanDTO.getBookId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("No se ha encontrado ningún libro con el ID: " + loanDTO.getBookId()));

        Book oldBook = existingLoan.getBook();

        // Si cambia el libro
        if (oldBook != null && !oldBook.getId().equals(newBook.getId())) {

            Integer oldCopies = oldBook.getAvailableCopies() == null ? 0 : oldBook.getAvailableCopies();
            oldBook.setAvailableCopies(oldCopies + 1);
            bookRepository.save(oldBook);

            if (newBook.getAvailableCopies() == null || newBook.getAvailableCopies() <= 0) {
                throw new BadRequestException("No hay copias disponibles del libro con ID: " + newBook.getId());
            }

            newBook.setAvailableCopies(newBook.getAvailableCopies() - 1);
            bookRepository.save(newBook);
        }

        existingLoan.setUser(user);
        existingLoan.setBook(newBook);

        // 🔥 Si cambia loanDate, recalcular dueDate
        if (loanDTO.getLoanDate() != null) {
            existingLoan.setLoanDate(loanDTO.getLoanDate());
            existingLoan.setDueDate(loanDTO.getLoanDate().plusDays(LOAN_DURATION_DAYS));
        }

        Loan updatedLoan = loanRepository.save(existingLoan);

        return LoanMapper.toDTO(updatedLoan);
    }

    /**
     * Elimina un préstamo del sistema.
     * <p>
     * Solo se permite eliminar si el libro ha sido devuelto
     * (returnedDate distinto de null).
     *
     * @param id ID del préstamo.
     * @throws ResourceNotFoundException si el préstamo no existe.
     * @throws BadRequestException       si el préstamo no ha sido devuelto.
     */
    @Transactional
    @Override
    public void deleteLoan(Integer id) {
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado ningún préstamo con el ID: " + id));
        if (loan.getReturnedDate() == null) {
            throw new BadRequestException("No se puede borrar el registro porque el libro no está devuelto");
        }
        loanRepository.delete(loan);
    }
}

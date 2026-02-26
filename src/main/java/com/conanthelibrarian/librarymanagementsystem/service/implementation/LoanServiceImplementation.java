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

import java.util.List;

/**
 * Implementación del servicio de gestión de préstamos.
 *
 * Esta clase contiene la lógica de negocio más compleja del sistema,
 * ya que gestiona relaciones entre usuarios y libros.
 *
 * Funcionalidades principales:
 * - Obtener todos los préstamos
 * - Obtener préstamo por ID
 * - Crear préstamo (verificando existencia de usuario y libro)
 * - Validar disponibilidad de copias antes de prestar
 * - Reducir el número de copias disponibles al crear préstamo
 * - Ajustar copias si se cambia el libro en una actualización
 * - Eliminar préstamo solo si el libro ha sido devuelto
 *
 * Anotaciones:
 * - @Transactional se usa para garantizar consistencia en operaciones
 *   que afectan a múltiples entidades (Libro + Préstamo).
 *
 * Excepciones:
 * - ResourceNotFoundException cuando usuario, libro o préstamo no existen
 * - BadRequestException cuando no hay copias disponibles
 */
@Service
public class LoanServiceImplementation implements LoanService {

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
        return loanRepository.findAll()
                .stream()
                .map(LoanMapper::toDTO)
                .toList();
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
        Loan loan = loanRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No se ha encontrado ningún préstamo con el ID: " + id)
        );

        return LoanMapper.toDTO(loan);
    }

    /**
     * Crea un nuevo préstamo.
     *
     * - Verifica que el usuario exista.
     * - Verifica que el libro exista.
     * - Comprueba que haya copias disponibles.
     * - Reduce en una unidad las copias disponibles del libro.
     *
     * @param loanDTO Datos del préstamo.
     * @return Préstamo creado en formato DTO.
     * @throws ResourceNotFoundException si usuario o libro no existen.
     * @throws BadRequestException si no hay copias disponibles.
     */
    @Transactional
    @Override
    public LoanDTO createLoan(LoanDTO loanDTO) {

        User user = userRepository.findById(loanDTO.getUserId()).orElseThrow(() ->
                new ResourceNotFoundException("No se ha encontrado ningún usuario con el ID: " + loanDTO.getUserId())
        );

        Book book = bookRepository.findById(loanDTO.getBookId()).orElseThrow(() ->
                new ResourceNotFoundException("No se ha encontrado ningún libro con el ID: " + loanDTO.getBookId())
        );

        if (book.getAvailableCopies() == null || book.getAvailableCopies() <= 0) {
            throw new BadRequestException("No hay copias disponibles del libro con ID: " + book.getId());
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        Loan loan = LoanMapper.toEntity(loanDTO);

        loan.setId(null);

        loan.setUser(user);
        loan.setBook(book);

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
     * @param id ID del préstamo a actualizar.
     * @param loanDTO Nuevos datos.
     * @return Préstamo actualizado en formato DTO.
     * @throws ResourceNotFoundException si préstamo, usuario o libro no existen.
     * @throws BadRequestException si el nuevo libro no tiene copias disponibles.
     */
    @Transactional
    @Override
    public LoanDTO updateLoan(Integer id, LoanDTO loanDTO) {

        Loan existingLoan = loanRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No se ha encontrado ningún préstamo con el ID: " + id)
        );

        User user = userRepository.findById(loanDTO.getUserId()).orElseThrow(() ->
                new ResourceNotFoundException("No se ha encontrado ningún usuario con el ID: " + loanDTO.getUserId())
        );

        Book newBook = bookRepository.findById(loanDTO.getBookId()).orElseThrow(() ->
                new ResourceNotFoundException("No se ha encontrado ningún libro con el ID: " + loanDTO.getBookId())
        );

        Book oldBook = existingLoan.getBook();

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
        existingLoan.setLoanDate(loanDTO.getLoanDate());
        existingLoan.setDueDate(loanDTO.getDueDate());

        Loan updatedLoan = loanRepository.save(existingLoan);

        return LoanMapper.toDTO(updatedLoan);
    }

    /**
     * Elimina un préstamo del sistema.
     *
     * Solo se permite eliminar si el libro ha sido devuelto
     * (returnedDate distinto de null).
     *
     * @param id ID del préstamo.
     * @throws ResourceNotFoundException si el préstamo no existe.
     * @throws BadRequestException si el préstamo no ha sido devuelto.
     */
    @Transactional
    @Override
    public void deleteLoan(Integer id) {

        Loan loan = loanRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(
                        "No se ha encontrado ningún préstamo con el ID: " + id
                )
        );

        if (loan.getReturnedDate() == null) {
            throw new BadRequestException(
                    "No se puede borrar el registro porque el libro no está devuelto"
            );
        }

        loanRepository.delete(loan);
    }
}

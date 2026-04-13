package com.conanthelibrarian.librarymanagementsystem.service.implementation;

import com.conanthelibrarian.librarymanagementsystem.dto.BookDTO;
import com.conanthelibrarian.librarymanagementsystem.dto.LoanDTO;
import com.conanthelibrarian.librarymanagementsystem.entity.Book;
import com.conanthelibrarian.librarymanagementsystem.entity.Loan;
import com.conanthelibrarian.librarymanagementsystem.entity.User;
import com.conanthelibrarian.librarymanagementsystem.exception.ResourceNotFoundException;
import com.conanthelibrarian.librarymanagementsystem.exception.BadRequestException;
import com.conanthelibrarian.librarymanagementsystem.mapper.BookMapper;
import com.conanthelibrarian.librarymanagementsystem.mapper.LoanMapper;
import com.conanthelibrarian.librarymanagementsystem.repository.BookRepository;
import com.conanthelibrarian.librarymanagementsystem.repository.LoanRepository;
import com.conanthelibrarian.librarymanagementsystem.repository.UserRepository;
import com.conanthelibrarian.librarymanagementsystem.service.LoanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class LoanServiceImplementation implements LoanService {

    private static final int LOAN_DURATION_DAYS = 7;

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public LoanServiceImplementation(LoanRepository loanRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<LoanDTO> getAllLoans() {
        log.info("Obteniendo todos los préstamos");
        return loanRepository.findAll().
                stream().
                map(LoanMapper::toDTO).
                toList();
    }

    @Override
    public LoanDTO getLoanById(Integer id) {
        log.info("Buscando préstamo con ID: {}", id);
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Préstamo no encontrado con ID: {}", id);
                    return new ResourceNotFoundException("No se ha encontrado ningún préstamo con el ID: " + id);
                }
                );
        return LoanMapper.toDTO(loan);
    }

    @Transactional
    @Override
    public LoanDTO createLoan(LoanDTO loanDTO) {
        log.info("Creando préstamo para userId={} y bookId={}", loanDTO.getUserId(), loanDTO.getBookId());

        User user = userRepository.findById(loanDTO.getUserId())
                .orElseThrow(() -> {
                    log.error("Usuario no encontrado con ID: {}", loanDTO.getUserId());
                    return new ResourceNotFoundException("No se ha encontrado ningún usuario con el ID: " + loanDTO.getUserId());
                });

        Book book = bookRepository.findById(loanDTO.getBookId())
                .orElseThrow(() -> {
                    log.error("Libro no encontrado con ID: {}", loanDTO.getBookId());
                    return new ResourceNotFoundException("No se ha encontrado ningún libro con el ID: " + loanDTO.getBookId());
                });

        if (book.getAvailableCopies() == null || book.getAvailableCopies() <= 0) {
            log.warn("Intento de préstamo sin stock. Libro ID: {}", book.getId());
            throw new BadRequestException("No hay copias disponibles del libro con ID: " + book.getId());
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        Loan loan = LoanMapper.toEntity(loanDTO);
        loan.setId(null);
        loan.setUser(user);
        loan.setBook(book);
        loan.setDueDate(loan.getLoanDate().plusDays(LOAN_DURATION_DAYS));

        Loan savedLoan = loanRepository.save(loan);

        log.info("Préstamo creado correctamente con ID: {}", savedLoan.getId());

        return LoanMapper.toDTO(savedLoan);
    }

    @Transactional
    @Override
    public LoanDTO updateLoan(Integer id, LoanDTO loanDTO) {
        log.info("Actualizando préstamo con ID: {}", id);

        Loan existingLoan = loanRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Préstamo no encontrado con ID: {}", id);
                    return new ResourceNotFoundException("No se ha encontrado ningún préstamo con el ID: " + id);
                });

        User user = userRepository.findById(loanDTO.getUserId())
                .orElseThrow(() -> {
                    log.error("Usuario no encontrado con ID: {}", loanDTO.getUserId());
                    return new ResourceNotFoundException("No se ha encontrado ningún usuario con el ID: " + loanDTO.getUserId());
                });

        Book newBook = bookRepository.findById(loanDTO.getBookId())
                .orElseThrow(() -> {
                    log.error("Libro no encontrado con ID: {}", loanDTO.getBookId());
                    return new ResourceNotFoundException("No se ha encontrado ningún libro con el ID: " + loanDTO.getBookId());
                });

        Book oldBook = existingLoan.getBook();

        if (oldBook != null && !oldBook.getId().equals(newBook.getId())) {
            log.info("Cambio de libro en préstamo {}: {} -> {}", id, oldBook.getId(), newBook.getId());

            oldBook.setAvailableCopies(oldBook.getAvailableCopies() + 1);
            bookRepository.save(oldBook);

            if (newBook.getAvailableCopies() == null || newBook.getAvailableCopies() <= 0) {
                log.warn("Nuevo libro sin stock ID: {}", newBook.getId());
                throw new BadRequestException("No hay copias disponibles del libro con ID: " + newBook.getId());
            }

            newBook.setAvailableCopies(newBook.getAvailableCopies() - 1);
            bookRepository.save(newBook);
        }

        existingLoan.setUser(user);
        existingLoan.setBook(newBook);

        if (loanDTO.getLoanDate() != null) {
            existingLoan.setLoanDate(loanDTO.getLoanDate());
            existingLoan.setDueDate(loanDTO.getLoanDate().plusDays(LOAN_DURATION_DAYS));
        }

        Loan updatedLoan = loanRepository.save(existingLoan);

        log.info("Préstamo actualizado correctamente ID: {}", id);

        return LoanMapper.toDTO(updatedLoan);
    }

    @Transactional
    @Override
    public void deleteLoan(Integer id) {
        log.info("Eliminando préstamo con ID: {}", id);

        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Préstamo no encontrado con ID: {}", id);
                    return new ResourceNotFoundException("No se ha encontrado ningún préstamo con el ID: " + id);
                });

        if (loan.getReturnedDate() == null) {
            log.warn("Intento de borrar préstamo no devuelto ID: {}", id);
            throw new BadRequestException("No se puede borrar el registro porque el libro no está devuelto");
        }

        loanRepository.delete(loan);

        log.info("Préstamo eliminado correctamente ID: {}", id);
    }

    @Transactional
    @Override
    public LoanDTO lendBookToUser(Integer bookId, Integer userId) {
        log.info("Prestando libro {} al usuario {}", bookId, userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("Usuario no encontrado ID: {}", userId);
                    return new ResourceNotFoundException("No se ha encontrado ningún usuario con el ID: " + userId);
                });

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> {
                    log.error("Libro no encontrado ID: {}", bookId);
                    return new ResourceNotFoundException("No se ha encontrado ningún libro con el ID: " + bookId);
                });

        if (book.getAvailableCopies() <= 0) {
            log.warn("Sin stock para préstamo libro ID: {}", bookId);
            throw new BadRequestException("No hay copias disponibles del libro con ID: " + bookId);
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(LOAN_DURATION_DAYS));

        Loan savedLoan = loanRepository.save(loan);

        log.info("Libro prestado correctamente. Loan ID: {}", savedLoan.getId());

        return LoanMapper.toDTO(savedLoan);
    }

    @Transactional
    @Override
    public LoanDTO returnBook(Integer loanId) {
        log.info("Devolviendo préstamo ID: {}", loanId);

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> {
                    log.error("Préstamo no encontrado ID: {}", loanId);
                    return new ResourceNotFoundException("No se ha encontrado ningún préstamo con el ID: " + loanId);
                });

        if (loan.getReturnedDate() != null) {
            log.warn("Préstamo ya devuelto ID: {}", loanId);
            throw new BadRequestException("Este préstamo ya está devuelto");
        }

        LocalDate today = LocalDate.now();
        LocalDate dueDate = loan.getDueDate();

        loan.setReturnedDate(today);

        long difference = ChronoUnit.DAYS.between(dueDate, today);

        BigDecimal basePrice = BigDecimal.valueOf(10);
        BigDecimal finalPrice;

        if (difference < 0) {
            finalPrice = basePrice.subtract(BigDecimal.valueOf(Math.abs(difference)));
        } else if (difference == 0) {
            finalPrice = basePrice;
        } else {
            finalPrice = basePrice.add(BigDecimal.valueOf(difference * 2L));
        }

        loan.setPrice(finalPrice);

        Book book = loan.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);

        loanRepository.save(loan);

        log.info("Préstamo devuelto correctamente ID: {}. Precio: {}", loanId, finalPrice);

        return LoanMapper.toDTO(loan);
    }

    @Override
    public List<BookDTO> getBorrowedBooksByUser(Integer userId) {
        log.info("Obteniendo libros prestados del usuario ID: {}", userId);

        if (!userRepository.existsById(userId)) {
            log.error("Usuario no encontrado ID: {}", userId);
            throw new ResourceNotFoundException("No se ha encontrado ningún usuario con el ID: " + userId);
        }

        List<Loan> loans = loanRepository.findByUserId(userId);

        List<BookDTO> books = new ArrayList<>();

        for (Loan loan : loans) {
            books.add(BookMapper.toDTO(loan.getBook()));
        }

        return books;
    }
}

package com.conanthelibrarian.librarymanagementsystem.controller;

import com.conanthelibrarian.librarymanagementsystem.dto.BookDTO;
import com.conanthelibrarian.librarymanagementsystem.dto.LoanDTO;
import com.conanthelibrarian.librarymanagementsystem.dto.UserDTO;
import com.conanthelibrarian.librarymanagementsystem.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST encargado de gestionar las operaciones relacionadas con préstamos.
 *
 * <p>Expone endpoints CRUD para crear, consultar, actualizar y eliminar préstamos.</p>
 *
 * <p>Este controlador trabaja con DTOs ({@link LoanDTO}) para evitar exponer
 * directamente las entidades JPA.</p>
 *
 * <p>IMPORTANTE: la lógica de negocio (comprobar stock, actualizar copias disponibles, etc.)
 * se gestiona en la capa Service, no aquí.</p>
 */
@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    /**
     * Constructor para inyección de dependencias.
     *
     * @param loanService servicio de préstamos
     */
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    /**
     * Obtiene todos los préstamos registrados en el sistema.
     *
     * @return lista de préstamos en formato DTO
     */
    @GetMapping
    public ResponseEntity<List<LoanDTO>> getAllLoans() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    /**
     * Obtiene un préstamo por su ID.
     *
     * @param id identificador del préstamo
     * @return préstamo encontrado en formato DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<LoanDTO> getLoanById(@PathVariable Integer id) {
        return ResponseEntity.ok(loanService.getLoanById(id));
    }

    /**
     * Crea un nuevo préstamo.
     *
     * <p>Los datos se validan automáticamente gracias a {@code @Valid}.</p>
     *
     * <p>IMPORTANTE: en la capa Service se aplican reglas como:</p>
     * <ul>
     *     <li>El usuario debe existir</li>
     *     <li>El libro debe existir</li>
     *     <li>El libro debe tener copias disponibles</li>
     *     <li>Se reduce availableCopies en 1</li>
     * </ul>
     *
     * @param loanDTO datos del préstamo a crear
     * @return préstamo creado
     */
    @PostMapping
    public ResponseEntity<LoanDTO> createLoan(@Valid @RequestBody LoanDTO loanDTO) {
        return ResponseEntity.ok(loanService.createLoan(loanDTO));
    }

    /**
     * Actualiza un préstamo existente.
     *
     * <p>IMPORTANTE: si el préstamo cambia de libro, la capa Service se encarga de:</p>
     * <ul>
     *     <li>Devolver una copia al libro anterior</li>
     *     <li>Comprobar stock del nuevo libro</li>
     *     <li>Reducir copias del nuevo libro</li>
     * </ul>
     *
     * @param id      identificador del préstamo
     * @param loanDTO datos nuevos del préstamo
     * @return préstamo actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<LoanDTO> updateLoan(@PathVariable Integer id, @Valid @RequestBody LoanDTO loanDTO) {
        return ResponseEntity.ok(loanService.updateLoan(id, loanDTO));
    }

    /**
     * Elimina un préstamo por su ID.
     *
     * <p>En la lógica del sistema esto equivale a devolver el libro.</p>
     *
     * <p>IMPORTANTE: la capa Service se encarga de incrementar availableCopies en 1.</p>
     *
     * @param id identificador del préstamo
     * @return respuesta 204 No Content si se elimina correctamente
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Integer id) {
        loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Recupera todos los libros actualmente en préstamo.
     *
     * <p>Un libro se considera actualmente prestado si existe
     * al menos un registro en la tabla Loan asociado a él.</p>
     *
     * @return lista de libros prestados
     */
    @GetMapping("/books-on-loan")
    public ResponseEntity<List<BookDTO>> getBooksCurrentlyOnLoan() {
        return ResponseEntity.ok(loanService.getBooksCurrentlyOnLoan());
    }

    /**
     * Recupera todos los usuarios que tienen más de X préstamos.
     *
     * <p>Ejemplo de uso:</p>
     * <pre>
     * GET /api/loans/users-with-more-than?minLoans=2
     * </pre>
     *
     * @param minLoans número mínimo de préstamos
     * @return lista de usuarios que superan ese número
     */
    @GetMapping("/users-with-more-than")
    public ResponseEntity<List<UserDTO>> getUsersWithMoreThanXLoans(
            @RequestParam Long minLoans) {

        return ResponseEntity.ok(
                loanService.getUsersWithMoreThanXLoans(minLoans)
        );
    }
}

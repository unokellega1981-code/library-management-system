package com.conanthelibrarian.librarymanagementsystem.controller;

import com.conanthelibrarian.librarymanagementsystem.dto.LoanDTO;
import com.conanthelibrarian.librarymanagementsystem.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de préstamos.
 * <p>
 * Expone los endpoints HTTP relacionados con la entidad Loan.
 * Actúa como capa intermedia entre el cliente (API REST)
 * y la capa de servicio.
 * <p>
 * Base URL: /api/loans
 */
@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    /**
     * Obtiene todos los préstamos registrados.
     *
     * @return Lista de préstamos en formato DTO.
     */
    @GetMapping
    public ResponseEntity<List<LoanDTO>> getAllLoans() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    /**
     * Obtiene un préstamo por su ID.
     *
     * @param id ID del préstamo.
     * @return Préstamo encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LoanDTO> getLoanById(@PathVariable Integer id) {
        return ResponseEntity.ok(loanService.getLoanById(id));
    }

    /**
     * Crea un nuevo préstamo.
     *
     * @param loanDTO Datos del préstamo a crear.
     * @return Préstamo creado.
     */
    @PostMapping
    public ResponseEntity<LoanDTO> createLoan(@Valid @RequestBody LoanDTO loanDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(loanService.createLoan(loanDTO));
    }

    /**
     * Actualiza un préstamo existente.
     *
     * @param id      ID del préstamo.
     * @param loanDTO Datos actualizados.
     * @return Préstamo actualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LoanDTO> updateLoan(@PathVariable Integer id, @Valid @RequestBody LoanDTO loanDTO) {
        return ResponseEntity.ok(loanService.updateLoan(id, loanDTO));
    }

    /**
     * Elimina un préstamo.
     *
     * @param id ID del préstamo a eliminar.
     * @return Respuesta sin contenido (204).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Integer id) {
        loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }
}

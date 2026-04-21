package com.conanthelibrarian.librarymanagementsystem.controller;

import com.conanthelibrarian.librarymanagementsystem.dto.BookDTO;
import com.conanthelibrarian.librarymanagementsystem.dto.LoanDTO;
import com.conanthelibrarian.librarymanagementsystem.mapper.LoanMapper;
import com.conanthelibrarian.librarymanagementsystem.service.LoanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;
    LoanMapper loanMapper;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public ResponseEntity<List<LoanDTO>> getAllLoans() {
        log.info("GET /api/loans - Recuperando todos los préstamos");
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanDTO> getLoanById(@PathVariable Integer id) {
        log.info("GET /api/loans/{} - Buscando préstamo", id);
        return ResponseEntity.ok(loanService.getLoanById(id));
    }

    @PostMapping("/createloan")
    public ResponseEntity<LoanDTO> createLoan(@RequestBody LoanDTO loanDTO) {
        log.info("POST /api/loans/createloan - Creando préstamo: {}", loanDTO);
        loanMapper.toEntity(loanDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(loanService.createLoan(loanDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanDTO> updateLoan(@PathVariable Integer id, @RequestBody LoanDTO loanDTO) {
        log.info("PUT /api/loans/{} - Actualizando préstamo con datos: {}", id, loanDTO);
        loanMapper.toEntity(loanDTO);
        return ResponseEntity.ok(loanService.updateLoan(id, loanDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Integer id) {
        log.warn("DELETE /api/loans/{} - Eliminando préstamo", id);
        loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/lend")
    public ResponseEntity<LoanDTO> lendBookToUser(
            @RequestParam Integer bookId,
            @RequestParam Integer userId) {
        log.info("POST /api/loans/lend - Prestando libro {} al usuario {}", bookId, userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(loanService.lendBookToUser(bookId, userId));
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<LoanDTO> returnBook(@PathVariable Integer id) {
        log.info("POST /api/loans/{}/return - Devolviendo préstamo", id);
        return ResponseEntity.ok(loanService.returnBook(id));
    }

    @GetMapping("/user/{userId}/books")
    public ResponseEntity<List<BookDTO>> getBorrowedBooksByUser(@PathVariable Integer userId) {
        log.info("GET /api/loans/user/{}/books - Libros prestados al usuario", userId);
        return ResponseEntity.ok(loanService.getBorrowedBooksByUser(userId));
    }
}

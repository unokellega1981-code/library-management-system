package com.conanthelibrarian.librarymanagementsystem.controller;

import com.conanthelibrarian.librarymanagementsystem.dto.BookDTO;
import com.conanthelibrarian.librarymanagementsystem.dto.LoanDTO;
import com.conanthelibrarian.librarymanagementsystem.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public ResponseEntity<List<LoanDTO>> getAllLoans() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanDTO> getLoanById(@PathVariable Integer id) {
        return ResponseEntity.ok(loanService.getLoanById(id));
    }

    @PostMapping("/createloan")
    public ResponseEntity<LoanDTO> createLoan(@RequestBody LoanDTO loanDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(loanService.createLoan(loanDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanDTO> updateLoan(@PathVariable Integer id, @RequestBody LoanDTO loanDTO) {
        return ResponseEntity.ok(loanService.updateLoan(id, loanDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Integer id) {
        loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/lend")
    public ResponseEntity<LoanDTO> lendBookToUser(
            @RequestParam Integer bookId,
            @RequestParam Integer userId) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(loanService.lendBookToUser(bookId, userId));
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<LoanDTO> returnBook(@PathVariable Integer id) {
        return ResponseEntity.ok(loanService.returnBook(id));
    }

    @GetMapping("/user/{userId}/books")
    public ResponseEntity<List<BookDTO>> getBorrowedBooksByUser(@PathVariable Integer userId) {

        List<BookDTO> books = loanService.getBorrowedBooksByUser(userId);

        return ResponseEntity.ok(books);
    }
}

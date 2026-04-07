package com.conanthelibrarian.librarymanagementsystem.service;

import com.conanthelibrarian.librarymanagementsystem.dto.BookDTO;
import com.conanthelibrarian.librarymanagementsystem.dto.LoanDTO;

import java.util.List;

public interface LoanService {

    List<LoanDTO> getAllLoans();

    LoanDTO getLoanById(Integer id);

    LoanDTO createLoan(LoanDTO loanDTO);

    LoanDTO updateLoan(Integer id, LoanDTO loanDTO);

    void deleteLoan(Integer id);

    LoanDTO lendBookToUser(Integer bookId, Integer userId);

    LoanDTO returnBook(Integer loanId);

    List<BookDTO> getBorrowedBooksByUser(Integer userId);
}

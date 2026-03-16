package com.conanthelibrarian.librarymanagementsystem.controller;

import com.conanthelibrarian.librarymanagementsystem.dto.LoanDTO;
import com.conanthelibrarian.librarymanagementsystem.service.LoanService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests unitarios para {@link LoanController}.
 *
 * <p>
 * Verifica el funcionamiento de los endpoints REST
 * relacionados con los préstamos de libros.
 * </p>
 */
@WebMvcTest(LoanController.class)
class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService loanService;

    @Autowired
    private ObjectMapper objectMapper;

    private LoanDTO loan;

    @BeforeEach
    void setUp() {
        loan = new LoanDTO();
        loan.setId(1);
        loan.setUserId(1);
        loan.setBookId(2);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(7));
    }

    /**
     * Comprueba la recuperación de todos los préstamos.
     */
    @Test
    void getAllLoans_shouldReturnList() throws Exception {

        Mockito.when(loanService.getAllLoans()).thenReturn(List.of(loan));

        mockMvc.perform(get("/api/loans"))
                .andExpect(status().isOk());
    }

    /**
     * Comprueba la creación de un préstamo.
     */
    @Test
    void createLoan_shouldReturnCreatedLoan() throws Exception {

        Mockito.when(loanService.createLoan(any(LoanDTO.class))).thenReturn(loan);

        mockMvc.perform(post("/api/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loan)))
                .andExpect(status().isCreated());
    }

    /**
     * Comprueba la devolución de un préstamo.
     */
    @Test
    void returnBook_shouldReturnUpdatedLoan() throws Exception {

        Mockito.when(loanService.returnBook(anyInt())).thenReturn(loan);

        mockMvc.perform(post("/api/loans/1/return"))
                .andExpect(status().isOk());
    }

}

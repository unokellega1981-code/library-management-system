package com.conanthelibrarian.librarymanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoanDTO {

    private Integer id;

    private Integer userId;

    private Integer bookId;

    private LocalDate loanDate;

    private LocalDate dueDate;

    private LocalDate returnedDate;

    private BigDecimal price;
}

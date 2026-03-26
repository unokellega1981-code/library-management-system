package com.conanthelibrarian.librarymanagementsystem.dto;

import com.conanthelibrarian.librarymanagementsystem.constants.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookDTO {

    private Integer id;

    private String title;

    private String author;

    private String isbn;

    private Genre genre;

    private Integer availableCopies;
}

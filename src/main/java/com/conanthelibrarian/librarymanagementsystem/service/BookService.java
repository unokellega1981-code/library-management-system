package com.conanthelibrarian.librarymanagementsystem.service;

import com.conanthelibrarian.librarymanagementsystem.constants.Genre;
import com.conanthelibrarian.librarymanagementsystem.dto.BookDTO;

import java.util.List;

public interface BookService {

    List<BookDTO> getAllBooks();

    BookDTO getBookById(Integer id);

    BookDTO createBook(BookDTO bookDTO);

    BookDTO updateBook(Integer id, BookDTO bookDTO);

    void deleteBook(Integer id);

    List<BookDTO> getBooksByGenre(Genre genre);

    List<BookDTO> getBooksCurrentlyOnLoan();

    List<BookDTO> getBooksByAuthor(String author);
}

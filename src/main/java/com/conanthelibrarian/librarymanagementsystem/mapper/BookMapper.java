package com.conanthelibrarian.librarymanagementsystem.mapper;

import com.conanthelibrarian.librarymanagementsystem.dto.BookDTO;
import com.conanthelibrarian.librarymanagementsystem.entity.Book;
import com.conanthelibrarian.librarymanagementsystem.exception.BadRequestException;

public class BookMapper {

    private BookMapper() {
    }

    public static BookDTO toDTO(Book book) {
        if (book == null) return null;
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getGenre(),
                book.getAvailableCopies()
        );
    }

    public static Book toEntity(BookDTO bookDTO) {

        if (bookDTO == null) {
            throw new BadRequestException("El cuerpo de la petición no puede ser null");
        }

        if (bookDTO.getTitle() == null || bookDTO.getTitle().isBlank()) {
            throw new BadRequestException("El campo 'title' es obligatorio");
        }

        if (bookDTO.getAuthor() == null || bookDTO.getAuthor().isBlank()) {
            throw new BadRequestException("El campo 'author' es obligatorio");
        }

        if (bookDTO.getIsbn() == null || bookDTO.getIsbn().isBlank()) {
            throw new BadRequestException("El campo 'isbn' es obligatorio");
        }

        if (bookDTO.getGenre() == null) {
            throw new BadRequestException("El campo 'genre' es obligatorio");
        }

        if (bookDTO.getAvailableCopies() == null || bookDTO.getAvailableCopies() < 0) {
            throw new BadRequestException("El campo 'availableCopies' es obligatorio y debe ser mayor a 0");
        }

        return new Book(
                bookDTO.getId(),
                bookDTO.getTitle(),
                bookDTO.getAuthor(),
                bookDTO.getIsbn(),
                bookDTO.getGenre(),
                bookDTO.getAvailableCopies()
        );
    }
}

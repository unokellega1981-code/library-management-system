package com.conanthelibrarian.librarymanagementsystem.mapper;

import com.conanthelibrarian.librarymanagementsystem.dto.BookDTO;
import com.conanthelibrarian.librarymanagementsystem.entity.Book;

/**
 * Mapper para la entidad Book.
 *
 * <p>
 * Permite convertir entre la entidad Book y su DTO (BookDTO) y viceversa.
 * Esto facilita separar la capa de persistencia de la capa de presentación.
 * </p>
 */
public class BookMapper {

    /**
     * Constructor privado para evitar instanciación
     */
    private BookMapper() {
    }

    /**
     * Convierte Book → BookDTO
     */
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

    /**
     * Convierte BookDTO → Book
     */
    public static Book toEntity(BookDTO bookDTO) {
        if (bookDTO == null) return null;

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

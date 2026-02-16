package com.conanthelibrarian.librarymanagementsystem.mapper;

import com.conanthelibrarian.librarymanagementsystem.dto.BookDTO;
import com.conanthelibrarian.librarymanagementsystem.entity.Book;

/**
 * Clase encargada de convertir entre {@link Book} (entidad) y {@link BookDTO} (DTO).
 *
 * <p>Los mappers permiten separar la capa de persistencia (entidades JPA)
 * de la capa de API (DTOs), evitando exponer directamente las entidades en los controllers.</p>
 *
 * <p>Esta clase contiene métodos estáticos para evitar instanciación y porque no mantiene estado.</p>
 */
public class BookMapper {

    private BookMapper() {
        // Constructor privado para evitar instanciación.
    }

    /**
     * Convierte una entidad {@link Book} en un {@link BookDTO}.
     *
     * @param book entidad Book
     * @return DTO equivalente o null si el parámetro es null
     */
    public static BookDTO toDTO(Book book) {
        if (book == null) {
            return null;
        }

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
     * Convierte un {@link BookDTO} en una entidad {@link Book}.
     *
     * @param bookDTO DTO BookDTO
     * @return entidad equivalente o null si el parámetro es null
     */
    public static Book toEntity(BookDTO bookDTO) {
        if (bookDTO == null) {
            return null;
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

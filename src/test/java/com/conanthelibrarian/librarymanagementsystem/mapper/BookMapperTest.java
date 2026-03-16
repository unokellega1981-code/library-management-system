package com.conanthelibrarian.librarymanagementsystem.mapper;

import com.conanthelibrarian.librarymanagementsystem.constants.Genre;
import com.conanthelibrarian.librarymanagementsystem.dto.BookDTO;
import com.conanthelibrarian.librarymanagementsystem.entity.Book;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para {@link BookMapper}.
 *
 * <p>
 * Verifica que la conversión entre {@link Book} y {@link BookDTO}
 * se realiza correctamente en ambos sentidos.
 * </p>
 *
 * <p>
 * También comprueba el comportamiento cuando los valores
 * de entrada son {@code null}.
 * </p>
 */
class BookMapperTest {

    /**
     * Comprueba la conversión de Entity a DTO.
     */
    @Test
    void shouldConvertEntityToDTO() {

        Book book = new Book();
        book.setId(1);
        book.setTitle("1984");
        book.setAuthor("George Orwell");
        book.setIsbn("123456");
        book.setGenre(Genre.FANTASY);
        book.setAvailableCopies(3);

        BookDTO dto = BookMapper.toDTO(book);

        assertEquals(book.getId(), dto.getId());
        assertEquals(book.getTitle(), dto.getTitle());
        assertEquals(book.getAuthor(), dto.getAuthor());
        assertEquals(book.getIsbn(), dto.getIsbn());
        assertEquals(book.getGenre(), dto.getGenre());
        assertEquals(book.getAvailableCopies(), dto.getAvailableCopies());
    }

    /**
     * Comprueba la conversión de DTO a Entity.
     */
    @Test
    void shouldConvertDTOToEntity() {

        BookDTO dto = new BookDTO();
        dto.setId(1);
        dto.setTitle("Dune");
        dto.setAuthor("Frank Herbert");
        dto.setIsbn("654321");
        dto.setGenre(Genre.SCIENCE_FICTION);
        dto.setAvailableCopies(5);

        Book book = BookMapper.toEntity(dto);

        assertEquals(dto.getId(), book.getId());
        assertEquals(dto.getTitle(), book.getTitle());
        assertEquals(dto.getAuthor(), book.getAuthor());
        assertEquals(dto.getIsbn(), book.getIsbn());
        assertEquals(dto.getGenre(), book.getGenre());
        assertEquals(dto.getAvailableCopies(), book.getAvailableCopies());
    }

    /**
     * Comprueba que si la entidad es null
     * el mapper devuelve null.
     */
    @Test
    void shouldReturnNullWhenEntityIsNull() {

        BookDTO dto = BookMapper.toDTO(null);

        assertNull(dto);
    }

    /**
     * Comprueba que si el DTO es null
     * el mapper devuelve null.
     */
    @Test
    void shouldReturnNullWhenDTOIsNull() {

        Book book = BookMapper.toEntity(null);

        assertNull(book);
    }

}

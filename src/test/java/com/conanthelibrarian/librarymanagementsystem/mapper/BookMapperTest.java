package com.conanthelibrarian.librarymanagementsystem.mapper;

import com.conanthelibrarian.librarymanagementsystem.constants.Genre;
import com.conanthelibrarian.librarymanagementsystem.dto.BookDTO;
import com.conanthelibrarian.librarymanagementsystem.entity.Book;
import com.conanthelibrarian.librarymanagementsystem.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookMapperTest {

    @Test
    void shouldConvertEntityToDTO() {

        // GIVEN
        Book book = new Book();
        book.setId(1);
        book.setTitle("1984");
        book.setAuthor("George Orwell");
        book.setIsbn("123456");
        book.setGenre(Genre.FANTASY);
        book.setAvailableCopies(3);

        // WHEN
        BookDTO bookDTO = BookMapper.toDTO(book);

        // THEN
        assertEquals(book.getId(), bookDTO.getId());
        assertEquals(book.getTitle(), bookDTO.getTitle());
        assertEquals(book.getAuthor(), bookDTO.getAuthor());
        assertEquals(book.getIsbn(), bookDTO.getIsbn());
        assertEquals(book.getGenre(), bookDTO.getGenre());
        assertEquals(book.getAvailableCopies(), bookDTO.getAvailableCopies());
    }

    @Test
    void shouldConvertDTOToEntity() {

        // GIVEN
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(1);
        bookDTO.setTitle("Dune");
        bookDTO.setAuthor("Frank Herbert");
        bookDTO.setIsbn("654321");
        bookDTO.setGenre(Genre.SCIENCE_FICTION);
        bookDTO.setAvailableCopies(5);

        // WHEN
        Book book = BookMapper.toEntity(bookDTO);

        // THEN
        assertEquals(bookDTO.getId(), book.getId());
        assertEquals(bookDTO.getTitle(), book.getTitle());
        assertEquals(bookDTO.getAuthor(), book.getAuthor());
        assertEquals(bookDTO.getIsbn(), book.getIsbn());
        assertEquals(bookDTO.getGenre(), book.getGenre());
        assertEquals(bookDTO.getAvailableCopies(), book.getAvailableCopies());
    }

    @Test
    void shouldReturnNullWhenEntityIsNull() {

        // WHEN
        BookDTO bookDTO = BookMapper.toDTO(null);

        // THEN
        assertNull(bookDTO);
    }

    @Test
    void shouldThrowExceptionWhenDTOIsNull() {

        // THEN
        assertThrows(BadRequestException.class, () -> BookMapper.toEntity(null));
    }
}

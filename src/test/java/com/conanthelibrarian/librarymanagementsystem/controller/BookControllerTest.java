package com.conanthelibrarian.librarymanagementsystem.controller;

import com.conanthelibrarian.librarymanagementsystem.constants.Genre;
import com.conanthelibrarian.librarymanagementsystem.dto.BookDTO;
import com.conanthelibrarian.librarymanagementsystem.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private BookDTO book1;
    private BookDTO book2;

    @BeforeEach
    void setUp() {
        book1 = new BookDTO(1, "Libro A", "Autor A", "547467", Genre.FANTASY, 4);
        book2 = new BookDTO(2, "Libro B", "Autor B", "35747", Genre.SCIENCE_FICTION, 6);
    }

    @Test
    void getAllBooks_shouldReturnList() throws Exception {

        Mockito.when(bookService.getAllBooks()).thenReturn(List.of(book1, book2));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getBookById_shouldReturnBook() throws Exception {

        Mockito.when(bookService.getBookById(1)).thenReturn(book1);

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Libro A")));
    }

    @Test
    void createBook_shouldReturnCreatedBook() throws Exception {

        Mockito.when(bookService.createBook(any(BookDTO.class))).thenReturn(book1);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book1)))
                .andExpect(status().isCreated());
    }

    @Test
    void updateBook_shouldReturnUpdatedBook() throws Exception {

        Mockito.when(bookService.updateBook(anyInt(), any(BookDTO.class))).thenReturn(book1);

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book1)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBook_shouldReturnNoContent() throws Exception {

        Mockito.doNothing().when(bookService).deleteBook(1);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getBooksByGenre_shouldReturnList() throws Exception {

        Mockito.when(bookService.getBooksByGenre(Genre.SCIENCE_FICTION)).thenReturn(List.of(book1));

        mockMvc.perform(get("/api/books/genre/SCIENCE_FICTION"))
                .andExpect(status().isOk());
    }

    @Test
    void getBooksCurrentlyOnLoan_shouldReturnList() throws Exception {

        Mockito.when(bookService.getBooksCurrentlyOnLoan()).thenReturn(List.of(book2));

        mockMvc.perform(get("/api/books/on-loan"))
                .andExpect(status().isOk());
    }

    @Test
    void getBooksByAuthor_shouldReturnList() throws Exception {

        Mockito.when(bookService.getBooksByAuthor("George Orwell"))
                .thenReturn(List.of(book1));

        mockMvc.perform(get("/api/books/author/George Orwell"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}

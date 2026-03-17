package com.conanthelibrarian.librarymanagementsystem.e2e;

import com.conanthelibrarian.librarymanagementsystem.constants.Genre;
import com.conanthelibrarian.librarymanagementsystem.constants.Role;
import com.conanthelibrarian.librarymanagementsystem.dto.BookDTO;
import com.conanthelibrarian.librarymanagementsystem.dto.LoanDTO;
import com.conanthelibrarian.librarymanagementsystem.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import java.time.LocalDate;

/**
 * Pruebas End-to-End (E2E) del sistema de gestión de biblioteca.
 *
 * <p>
 * Esta clase verifica el funcionamiento completo del sistema simulando
 * el comportamiento real de un usuario a través de peticiones HTTP.
 * </p>
 *
 * <p>
 * A diferencia de los tests unitarios:
 * </p>
 * <ul>
 *     <li>No se utilizan mocks</li>
 *     <li>Se levanta el contexto completo de Spring</li>
 *     <li>Se prueban todos los componentes integrados (Controller + Service + Repository + DB)</li>
 * </ul>
 *
 * <p>
 * Se validan los siguientes flujos:
 * </p>
 * <ul>
 *     <li>Gestión de usuarios</li>
 *     <li>Gestión de libros</li>
 *     <li>Préstamos y devoluciones</li>
 *     <li>Búsquedas y consultas avanzadas</li>
 * </ul>
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class LibraryEndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static int userId;
    private static int bookId;
    private static int loanId;

    /**
     * Crea un usuario en el sistema.
     */
    @Test
    @Order(1)
    void createUser_shouldWork() throws Exception {

        UserDTO userDTO = new UserDTO(null, "Carlos Perez", "carlosperez@email.com", "1234", Role.STAFF);

        String response = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Carlos Perez")))
                .andReturn()
                .getResponse()
                .getContentAsString();

        userId = objectMapper.readTree(response).get("id").asInt();
    }

    /**
     * Crea un libro en el sistema.
     */
    @Test
    @Order(2)
    void createBook_shouldWork() throws Exception {

        BookDTO bookDTO = new BookDTO(null, "Dune", "Frank Herbert", "12345", Genre.SCIENCE_FICTION, 5);

        String response = mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("Dune")))
                .andReturn()
                .getResponse()
                .getContentAsString();

        bookId = objectMapper.readTree(response).get("id").asInt();
    }

    /**
     * Realiza un préstamo de un libro a un usuario.
     */
    @Test
    @Order(3)
    void createLoan_shouldWork() throws Exception {

        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setUserId(userId);
        loanDTO.setBookId(bookId);
        loanDTO.setLoanDate(LocalDate.now());
        loanDTO.setDueDate(LocalDate.now().plusDays(7));

        String response = mockMvc.perform(post("/api/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        loanId = objectMapper.readTree(response).get("id").asInt();
    }

    /**
     * Comprueba que el libro aparece como prestado.
     */
    @Test
    @Order(4)
    void getBooksOnLoan_shouldReturnBook() throws Exception {

        mockMvc.perform(get("/api/books/on-loan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())));
    }

    /**
     * Comprueba la búsqueda de libros por autor.
     */
    @Test
    @Order(5)
    void getBooksByAuthor_shouldReturnResults() throws Exception {

        mockMvc.perform(get("/api/books/author/Frank Herbert"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].author", is("Frank Herbert")));
    }

    /**
     * Comprueba la búsqueda de usuarios por nombre.
     */
    @Test
    @Order(6)
    void getUserByName_shouldReturnUser() throws Exception {

        mockMvc.perform(get("/api/users/name/Carlos Perez"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Carlos Perez")));
    }

    /**
     * Devuelve el libro prestado.
     */
    @Test
    @Order(7)
    void returnBook_shouldWork() throws Exception {

        mockMvc.perform(post("/api/loans/" + loanId + "/return"))
                .andExpect(status().isOk());
    }

    /**
     * Elimina el libro.
     */
    @Test
    @Order(8)
    void deleteBook_shouldWork() throws Exception {

        // 1. Borrar préstamo
        mockMvc.perform(delete("/api/loans/1"))
                .andExpect(status().isNoContent());

        // 2. Borrar libro
        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());
    }

    /**
     * Elimina el usuario.
     */
    @Test
    @Order(9)
    void deleteUser_shouldWork() throws Exception {

        mockMvc.perform(delete("/api/users/" + userId))
                .andExpect(status().isNoContent());
    }
}

package com.conanthelibrarian.librarymanagementsystem.controller;

import com.conanthelibrarian.librarymanagementsystem.dto.UserDTO;
import com.conanthelibrarian.librarymanagementsystem.service.UserService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests unitarios para {@link UserController}.
 *
 * <p>
 * Comprueba el funcionamiento de los endpoints REST
 * relacionados con la gestión de usuarios.
 * </p>
 */
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDTO user;

    @BeforeEach
    void setUp() {
        user = new UserDTO(1, "Juan", "juan@email.com");
    }

    /**
     * Comprueba la recuperación de todos los usuarios.
     */
    @Test
    void getAllUsers_shouldReturnList() throws Exception {

        Mockito.when(userService.getAllUsers()).thenReturn(List.of(user));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());
    }

    /**
     * Comprueba la recuperación de un usuario por su ID.
     */
    @Test
    void getUserById_shouldReturnUser() throws Exception {

        Mockito.when(userService.getUserById(1)).thenReturn(user);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk());
    }

    /**
     * Comprueba la creación de un usuario.
     */
    @Test
    void createUser_shouldReturnCreatedUser() throws Exception {

        Mockito.when(userService.createUser(any(UserDTO.class))).thenReturn(user);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    /**
     * Comprueba la actualización de un usuario.
     */
    @Test
    void updateUser_shouldReturnUpdatedUser() throws Exception {

        Mockito.when(userService.updateUser(anyInt(), any(UserDTO.class))).thenReturn(user);

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    /**
     * Comprueba la eliminación de un usuario.
     */
    @Test
    void deleteUser_shouldReturnNoContent() throws Exception {

        Mockito.doNothing().when(userService).deleteUser(1);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }

}

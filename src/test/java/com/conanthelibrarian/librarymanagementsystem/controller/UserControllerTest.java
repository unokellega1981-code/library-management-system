package com.conanthelibrarian.librarymanagementsystem.controller;

import com.conanthelibrarian.librarymanagementsystem.constants.Role;
import com.conanthelibrarian.librarymanagementsystem.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO(1, "Juan", "juan@email.com", "1234", Role.MEMBER);
    }

    @Test
    void shouldReturnAllUsers() {

        // GIVEN
        when(userService.getAllUsers()).thenReturn(List.of(userDTO));

        // WHEN
        ResponseEntity<List<UserDTO>> response = userController.getAllUsers();

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(userService).getAllUsers();
    }

    @Test
    void shouldReturnUserById() {

        // GIVEN
        when(userService.getUserById(1)).thenReturn(userDTO);

        // WHEN
        ResponseEntity<UserDTO> response = userController.getUserById(1);

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Juan", response.getBody().getName());
        verify(userService).getUserById(1);
    }

    @Test
    void shouldCreateUser() {

        // GIVEN
        when(userService.createUser(any(UserDTO.class))).thenReturn(userDTO);

        // WHEN
        ResponseEntity<UserDTO> response = userController.createUser(userDTO);

        // THEN
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(userDTO, response.getBody());
        verify(userService).createUser(userDTO);
    }

    @Test
    void shouldUpdateUser() {

        // GIVEN
        when(userService.updateUser(1, userDTO)).thenReturn(userDTO);

        // WHEN
        ResponseEntity<UserDTO> response = userController.updateUser(1, userDTO);

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userDTO, response.getBody());
        verify(userService).updateUser(1, userDTO);
    }

    @Test
    void shouldDeleteUser() {

        // GIVEN
        doNothing().when(userService).deleteUser(1);

        // WHEN
        ResponseEntity<Void> response = userController.deleteUser(1);

        // THEN
        assertEquals(204, response.getStatusCodeValue());
        verify(userService).deleteUser(1);
    }

    @Test
    void shouldReturnUserByName() {

        // GIVEN
        when(userService.getUserByName("Juan")).thenReturn(userDTO);

        // WHEN
        ResponseEntity<UserDTO> response = userController.getUserByName("Juan");

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Juan", response.getBody().getName());
        verify(userService).getUserByName("Juan");
    }

    @Test
    void shouldReturnUsersWithMoreThanXActiveLoans() {

        // GIVEN
        when(userService.getUsersWithMoreThanXActiveLoans(2))
                .thenReturn(List.of(userDTO));

        // WHEN
        ResponseEntity<List<UserDTO>> response =
                userController.getUsersWithMoreThanXActiveLoans(2);

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(userService).getUsersWithMoreThanXActiveLoans(2);
    }

    @Test
    void shouldReturnUsersWithMoreThanXTotalLoans() {

        // GIVEN
        when(userService.getUsersWithMoreThanXTotalLoans(3))
                .thenReturn(List.of(userDTO));

        // WHEN
        ResponseEntity<List<UserDTO>> response =
                userController.getUsersWithMoreThanXTotalLoans(3);

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(userService).getUsersWithMoreThanXTotalLoans(3);
    }
}

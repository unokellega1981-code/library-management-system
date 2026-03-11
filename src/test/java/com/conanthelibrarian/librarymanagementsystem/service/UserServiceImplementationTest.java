package com.conanthelibrarian.librarymanagementsystem.service;

import com.conanthelibrarian.librarymanagementsystem.constants.Role;
import com.conanthelibrarian.librarymanagementsystem.dto.UserDTO;
import com.conanthelibrarian.librarymanagementsystem.entity.User;
import com.conanthelibrarian.librarymanagementsystem.exception.ResourceNotFoundException;
import com.conanthelibrarian.librarymanagementsystem.repository.LoanRepository;
import com.conanthelibrarian.librarymanagementsystem.repository.UserRepository;
import com.conanthelibrarian.librarymanagementsystem.service.implementation.UserServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests unitarios para la clase UserServiceImplementation.
 *
 * <p>
 * Estos tests verifican la lógica del servicio aislando
 * el acceso a la base de datos mediante mocks de los repositorios.
 * </p>
 *
 * <p>
 * Se comprueba:
 * - Obtención de usuarios
 * - Búsqueda por ID
 * - Creación de usuarios
 * - Actualización
 * - Eliminación
 * - Consultas por número de préstamos
 * </p>
 */
@ExtendWith(MockitoExtension.class)
class UserServiceImplementationTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private UserServiceImplementation userService;

    /**
     * Comprueba que el servicio devuelve todos los usuarios.
     */
    @Test
    void shouldReturnAllUsers() {

        User user1 = new User();
        User user2 = new User();

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<UserDTO> result = userService.getAllUsers();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    /**
     * Comprueba que se obtiene correctamente un usuario por ID.
     */
    @Test
    void shouldReturnUserById() {

        User user = new User();
        user.setId(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        UserDTO result = userService.getUserById(1);

        assertNotNull(result);
        verify(userRepository).findById(1);
    }

    /**
     * Comprueba que se lanza una excepción cuando el usuario no existe.
     */
    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.getUserById(1));
    }

    /**
     * Comprueba que se crea correctamente un usuario.
     */
    @Test
    void shouldCreateUser() {

        UserDTO dto = new UserDTO();
        dto.setName("Test User");
        dto.setEmail("test@test.com");

        User savedUser = new User();
        savedUser.setId(1);

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserDTO result = userService.createUser(dto);

        assertNotNull(result);
        verify(userRepository).save(any(User.class));
    }

    /**
     * Comprueba que se actualiza correctamente un usuario existente.
     */
    @Test
    void shouldUpdateUser() {

        User existingUser = new User();
        existingUser.setId(1);

        UserDTO dto = new UserDTO();
        dto.setName("Updated Name");
        dto.setEmail("updated@test.com");
        dto.setPassword("1234");
        dto.setRole(Role.MEMBER);

        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        UserDTO result = userService.updateUser(1, dto);

        assertNotNull(result);
        verify(userRepository).findById(1);
        verify(userRepository).save(existingUser);
    }

    /**
     * Comprueba que se lanza excepción si se intenta actualizar
     * un usuario que no existe.
     */
    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingUser() {

        UserDTO dto = new UserDTO();

        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.updateUser(1, dto));
    }

    /**
     * Comprueba que se elimina correctamente un usuario.
     */
    @Test
    void shouldDeleteUser() {

        User user = new User();
        user.setId(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        userService.deleteUser(1);

        verify(userRepository).delete(user);
    }

    /**
     * Comprueba que se lanza excepción al intentar eliminar
     * un usuario que no existe.
     */
    @Test
    void shouldThrowExceptionWhenDeletingNonExistingUser() {

        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.deleteUser(1));
    }

    /**
     * Comprueba que se devuelven los usuarios que tienen
     * más de X préstamos activos.
     */
    @Test
    void shouldReturnUsersWithMoreThanXActiveLoans() {

        User user = new User();

        when(loanRepository.findUsersWithMoreThanXActiveLoans(2))
                .thenReturn(List.of(user));

        List<UserDTO> result = userService.getUsersWithMoreThanXActiveLoans(2);

        assertEquals(1, result.size());
        verify(loanRepository).findUsersWithMoreThanXActiveLoans(2);
    }

    /**
     * Comprueba que se devuelven los usuarios que tienen
     * más de X préstamos totales.
     */
    @Test
    void shouldReturnUsersWithMoreThanXTotalLoans() {

        User user = new User();

        when(loanRepository.findUsersWithMoreThanXTotalLoans(3))
                .thenReturn(List.of(user));

        List<UserDTO> result = userService.getUsersWithMoreThanXTotalLoans(3);

        assertEquals(1, result.size());
        verify(loanRepository).findUsersWithMoreThanXTotalLoans(3);
    }
}
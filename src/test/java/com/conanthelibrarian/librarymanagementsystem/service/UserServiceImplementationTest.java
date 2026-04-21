package com.conanthelibrarian.librarymanagementsystem.service;

import com.conanthelibrarian.librarymanagementsystem.constants.Role;
import com.conanthelibrarian.librarymanagementsystem.dto.UserDTO;
import com.conanthelibrarian.librarymanagementsystem.entity.User;
import com.conanthelibrarian.librarymanagementsystem.exception.ResourceNotFoundException;
import com.conanthelibrarian.librarymanagementsystem.repository.LoanRepository;
import com.conanthelibrarian.librarymanagementsystem.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplementationTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private UserServiceImplementation userServiceImplementation;

    @Test
    void shouldReturnAllUsers() {

        // GIVEN
        User userUno = new User();
        User userDos = new User();

        when(userRepository.findAll()).thenReturn(List.of(userUno, userDos));

        // WHEN
        List<UserDTO> result = userServiceImplementation.getAllUsers();

        // THEN
        assertEquals(2, result.size());
        verify(userRepository).findAll();
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldReturnUserById() {

        // GIVEN
        User user = new User();
        user.setId(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // WHEN
        UserDTO result = userServiceImplementation.getUserById(1);

        // THEN
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(userRepository).findById(1);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        // GIVEN
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // WHEN + THEN
        assertThrows(ResourceNotFoundException.class,
                () -> userServiceImplementation.getUserById(1));

        verify(userRepository).findById(1);
    }

    @Test
    void shouldCreateUser() {

        // GIVEN
        UserDTO dto = new UserDTO();
        dto.setName("Test User");
        dto.setEmail("test@test.com");

        User savedUser = new User();
        savedUser.setId(1);
        savedUser.setName("Test User");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // WHEN
        UserDTO result = userServiceImplementation.createUser(dto);

        // THEN
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Test User", result.getName());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldUpdateUser() {

        // GIVEN
        User existingUser = new User();
        existingUser.setId(1);

        UserDTO dto = new UserDTO();
        dto.setName("Updated Name");
        dto.setEmail("updated@test.com");
        dto.setPassword("1234");
        dto.setRole(Role.MEMBER);

        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        // WHEN
        UserDTO result = userServiceImplementation.updateUser(1, dto);

        // THEN
        assertNotNull(result);
        verify(userRepository).findById(1);
        verify(userRepository).save(existingUser);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingUser() {

        // GIVEN
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // WHEN + THEN
        assertThrows(ResourceNotFoundException.class,
                () -> userServiceImplementation.updateUser(1, new UserDTO()));

        verify(userRepository).findById(1);
    }

    @Test
    void shouldDeleteUser() {

        // GIVEN
        User user = new User();
        user.setId(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // WHEN
        userServiceImplementation.deleteUser(1);

        // THEN
        verify(userRepository).delete(user);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingUser() {

        // GIVEN
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // WHEN + THEN
        assertThrows(ResourceNotFoundException.class,
                () -> userServiceImplementation.deleteUser(1));

        verify(userRepository).findById(1);
    }

    @Test
    void shouldReturnUsersWithMoreThanXActiveLoans() {

        // GIVEN
        User user = new User();

        when(loanRepository.findUsersWithMoreThanXActiveLoans(2))
                .thenReturn(List.of(user));

        // WHEN
        List<UserDTO> result = userServiceImplementation.getUsersWithMoreThanXActiveLoans(2);

        // THEN
        assertEquals(1, result.size());
        verify(loanRepository).findUsersWithMoreThanXActiveLoans(2);
    }

    @Test
    void shouldReturnUsersWithMoreThanXTotalLoans() {

        // GIVEN
        User user = new User();

        when(loanRepository.findUsersWithMoreThanXTotalLoans(3))
                .thenReturn(List.of(user));

        // WHEN
        List<UserDTO> result = userServiceImplementation.getUsersWithMoreThanXTotalLoans(3);

        // THEN
        assertEquals(1, result.size());
        verify(loanRepository).findUsersWithMoreThanXTotalLoans(3);
    }

    @Test
    void shouldReturnUserByName() {

        // GIVEN
        User user = new User();
        user.setName("Juan");

        when(userRepository.findByName("Juan"))
                .thenReturn(Optional.of(user));

        // WHEN
        UserDTO result = userServiceImplementation.getUserByName("Juan");

        // THEN
        assertNotNull(result);
        verify(userRepository).findByName("Juan");
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundByName() {

        // GIVEN
        when(userRepository.findByName("Juan"))
                .thenReturn(Optional.empty());

        // WHEN + THEN
        assertThrows(ResourceNotFoundException.class,
                () -> userServiceImplementation.getUserByName("Juan"));

        verify(userRepository).findByName("Juan");
    }
}

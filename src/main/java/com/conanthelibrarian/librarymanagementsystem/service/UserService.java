package com.conanthelibrarian.librarymanagementsystem.service;

import com.conanthelibrarian.librarymanagementsystem.dto.UserDTO;
import com.conanthelibrarian.librarymanagementsystem.entity.User;
import com.conanthelibrarian.librarymanagementsystem.exception.ResourceNotFoundException;
import com.conanthelibrarian.librarymanagementsystem.mapper.UserMapper;
import com.conanthelibrarian.librarymanagementsystem.repository.LoanRepository;
import com.conanthelibrarian.librarymanagementsystem.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final LoanRepository loanRepository;
    private final UserMapper userMapper = new UserMapper();

    public UserService(UserRepository userRepository, LoanRepository loanRepository) {
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
    }

    public List<UserDTO> getAllUsers() {
        log.info("Recuperando todos los usuarios");
        return userRepository.findAll().stream().map(userMapper::toDTO).toList();
    }

    public UserDTO getUserById(Integer id) {
        log.info("Buscando usuario con ID {}", id);

        User user = userRepository.findById(id).orElseThrow(() -> {
            log.warn("Usuario no encontrado con ID {}", id);
            return new ResourceNotFoundException("No se ha encontrado ningún usuario con el ID: " + id);
        });

        return userMapper.toDTO(user);
    }

    public UserDTO createUser(UserDTO userDTO) {
        log.info("Creando usuario: {}", userDTO);

        User user = userMapper.toEntity(userDTO);
        user.setId(null);

        User savedUser = userRepository.save(user);

        log.info("Usuario creado con ID {}", savedUser.getId());

        return userMapper.toDTO(savedUser);
    }

    public UserDTO updateUser(Integer id, UserDTO userDTO) {
        log.info("Actualizando usuario con ID {}", id);

        User existingUser = userRepository.findById(id).orElseThrow(() -> {
            log.warn("Intento de actualizar usuario inexistente con ID {}", id);
            return new ResourceNotFoundException("No se ha encontrado ningún usuario con el ID: " + id);
        });

        existingUser.setName(userDTO.getName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPassword(userDTO.getPassword());
        existingUser.setRole(userDTO.getRole());

        User updatedUser = userRepository.save(existingUser);

        log.info("Usuario actualizado con ID {}", id);

        return userMapper.toDTO(updatedUser);
    }

    public void deleteUser(Integer id) {
        log.warn("Eliminando usuario con ID {}", id);

        User existingUser = userRepository.findById(id).orElseThrow(() -> {
            log.warn("Intento de borrar usuario inexistente con ID {}", id);
            return new ResourceNotFoundException("No se ha encontrado ningún usuario con el ID: " + id);
        });

        userRepository.delete(existingUser);

        log.info("Usuario eliminado con ID {}", id);
    }

    public List<UserDTO> getUsersWithMoreThanXActiveLoans(int x) {
        log.info("Buscando usuarios con más de {} préstamos activos", x);

        return loanRepository.findUsersWithMoreThanXActiveLoans(x)
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    public List<UserDTO> getUsersWithMoreThanXTotalLoans(int x) {
        log.info("Buscando usuarios con más de {} préstamos totales", x);

        return loanRepository.findUsersWithMoreThanXTotalLoans(x)
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    public UserDTO getUserByName(String name) {
        log.info("Buscando usuario por nombre {}", name);

        User user = userRepository.findByName(name).orElseThrow(() -> {
            log.warn("Usuario no encontrado con nombre {}", name);
            return new ResourceNotFoundException("User not found with name: " + name);
        });

        return userMapper.toDTO(user);
    }
}

package com.conanthelibrarian.librarymanagementsystem.service.implementation;

import com.conanthelibrarian.librarymanagementsystem.dto.UserDTO;
import com.conanthelibrarian.librarymanagementsystem.entity.User;
import com.conanthelibrarian.librarymanagementsystem.exception.ResourceNotFoundException;
import com.conanthelibrarian.librarymanagementsystem.mapper.UserMapper;
import com.conanthelibrarian.librarymanagementsystem.repository.LoanRepository;
import com.conanthelibrarian.librarymanagementsystem.repository.UserRepository;
import com.conanthelibrarian.librarymanagementsystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final LoanRepository loanRepository;

    public UserServiceImplementation(UserRepository userRepository, LoanRepository loanRepository) {
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        log.info("Recuperando todos los usuarios");
        return userRepository.findAll().stream().map(UserMapper::toDTO).toList();
    }

    @Override
    public UserDTO getUserById(Integer id) {
        log.info("Buscando usuario con ID {}", id);

        User user = userRepository.findById(id).orElseThrow(() -> {
            log.warn("Usuario no encontrado con ID {}", id);
            return new ResourceNotFoundException("No se ha encontrado ningún usuario con el ID: " + id);
        });

        return UserMapper.toDTO(user);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        log.info("Creando usuario: {}", userDTO);

        User user = UserMapper.toEntity(userDTO);
        user.setId(null);

        User savedUser = userRepository.save(user);

        log.info("Usuario creado con ID {}", savedUser.getId());

        return UserMapper.toDTO(savedUser);
    }

    @Override
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

        return UserMapper.toDTO(updatedUser);
    }

    @Override
    public void deleteUser(Integer id) {
        log.warn("Eliminando usuario con ID {}", id);

        User existingUser = userRepository.findById(id).orElseThrow(() -> {
            log.warn("Intento de borrar usuario inexistente con ID {}", id);
            return new ResourceNotFoundException("No se ha encontrado ningún usuario con el ID: " + id);
        });

        userRepository.delete(existingUser);

        log.info("Usuario eliminado con ID {}", id);
    }

    @Override
    public List<UserDTO> getUsersWithMoreThanXActiveLoans(int x) {
        log.info("Buscando usuarios con más de {} préstamos activos", x);

        return loanRepository.findUsersWithMoreThanXActiveLoans(x)
                .stream()
                .map(UserMapper::toDTO)
                .toList();
    }

    @Override
    public List<UserDTO> getUsersWithMoreThanXTotalLoans(int x) {
        log.info("Buscando usuarios con más de {} préstamos totales", x);

        return loanRepository.findUsersWithMoreThanXTotalLoans(x)
                .stream()
                .map(UserMapper::toDTO)
                .toList();
    }

    @Override
    public UserDTO getUserByName(String name) {
        log.info("Buscando usuario por nombre {}", name);

        User user = userRepository.findByName(name).orElseThrow(() -> {
            log.warn("Usuario no encontrado con nombre {}", name);
            return new ResourceNotFoundException("User not found with name: " + name);
        });

        return UserMapper.toDTO(user);
    }
}

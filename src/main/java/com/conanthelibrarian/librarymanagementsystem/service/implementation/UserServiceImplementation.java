package com.conanthelibrarian.librarymanagementsystem.service.implementation;

import com.conanthelibrarian.librarymanagementsystem.dto.UserDTO;
import com.conanthelibrarian.librarymanagementsystem.entity.User;
import com.conanthelibrarian.librarymanagementsystem.exception.ResourceNotFoundException;
import com.conanthelibrarian.librarymanagementsystem.mapper.UserMapper;
import com.conanthelibrarian.librarymanagementsystem.repository.UserRepository;
import com.conanthelibrarian.librarymanagementsystem.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio {@link UserService}.
 *
 * <p>Esta clase contiene la lógica de negocio relacionada con usuarios y actúa como puente
 * entre los controllers (API) y los repositories (persistencia).</p>
 *
 * <p>En esta implementación se utiliza {@link UserRepository} para acceder a base de datos
 * y {@link UserMapper} para convertir entre entidades y DTOs.</p>
 */
@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;

    /**
     * Constructor para inyección de dependencias.
     *
     * <p>Spring inyectará automáticamente el {@link UserRepository} gracias a que
     * esta clase está anotada con {@link Service}.</p>
     *
     * @param userRepository repositorio de usuarios
     */
    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDTO getUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No se ha encontrado ningún usuario con el ID: " + id)
        );

        return UserMapper.toDTO(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);

        // Seguridad extra: el ID debe generarlo la base de datos.
        user.setId(null);

        User savedUser = userRepository.save(user);
        return UserMapper.toDTO(savedUser);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDTO updateUser(Integer id, UserDTO userDTO) {

        // 1) Comprobar que el usuario existe
        User existingUser = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No se ha encontrado ningún usuario con el ID: " + id)
        );

        // 2) Actualizar campos
        existingUser.setName(userDTO.getName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPassword(userDTO.getPassword());
        existingUser.setRole(userDTO.getRole());

        // 3) Guardar cambios
        User updatedUser = userRepository.save(existingUser);

        return UserMapper.toDTO(updatedUser);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteUser(Integer id) {

        // Comprobar que existe antes de borrar
        User existingUser = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No se ha encontrado ningún usuario con el ID: " + id)
        );

        userRepository.delete(existingUser);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserDTO> getUsersWithMoreThanXActiveLoans(Integer minLoans) {
        return userRepository.findUsersWithMoreThanXActiveLoans(minLoans)
                .stream()
                .map(UserMapper::toDTO)
                .toList();
    }
}

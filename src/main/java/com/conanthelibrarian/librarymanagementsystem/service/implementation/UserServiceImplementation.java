package com.conanthelibrarian.librarymanagementsystem.service.implementation;

import com.conanthelibrarian.librarymanagementsystem.dto.UserDTO;
import com.conanthelibrarian.librarymanagementsystem.entity.User;
import com.conanthelibrarian.librarymanagementsystem.exception.ResourceNotFoundException;
import com.conanthelibrarian.librarymanagementsystem.mapper.UserMapper;
import com.conanthelibrarian.librarymanagementsystem.repository.LoanRepository;
import com.conanthelibrarian.librarymanagementsystem.repository.UserRepository;
import com.conanthelibrarian.librarymanagementsystem.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio de gestión de usuarios.
 * <p>
 * Contiene la lógica de negocio asociada a la entidad User.
 * Gestiona las operaciones CRUD básicas y transforma los datos
 * entre entidades y DTOs usando UserMapper.
 * <p>
 * Responsabilidades:
 * - Obtener todos los usuarios
 * - Obtener usuario por ID
 * - Crear usuarios
 * - Actualizar usuarios
 * - Eliminar usuarios
 * <p>
 * Lanza:
 * - ResourceNotFoundException cuando el usuario no existe
 * <p>
 * Nota:
 * En un entorno real, el password no debería exponerse en el DTO.
 */
@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final LoanRepository loanRepository;

    public UserServiceImplementation(UserRepository userRepository, LoanRepository loanRepository) {
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
    }

    /**
     * Obtiene todos los usuarios registrados.
     *
     * @return Lista de usuarios en formato DTO.
     */
    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .toList();
    }

    /**
     * Obtiene un usuario por su identificador.
     *
     * @param id ID del usuario.
     * @return Usuario encontrado en formato DTO.
     * @throws ResourceNotFoundException si el usuario no existe.
     */
    @Override
    public UserDTO getUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No se ha encontrado ningún usuario con el ID: " + id)
        );

        return UserMapper.toDTO(user);
    }

    /**
     * Crea un nuevo usuario en el sistema.
     * El ID se genera automáticamente.
     *
     * @param userDTO Datos del usuario a crear.
     * @return Usuario creado en formato DTO.
     */
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);

        user.setId(null);

        User savedUser = userRepository.save(user);
        return UserMapper.toDTO(savedUser);
    }

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param id      ID del usuario a actualizar.
     * @param userDTO Nuevos datos.
     * @return Usuario actualizado en formato DTO.
     * @throws ResourceNotFoundException si el usuario no existe.
     */
    @Override
    public UserDTO updateUser(Integer id, UserDTO userDTO) {

        User existingUser = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No se ha encontrado ningún usuario con el ID: " + id)
        );

        existingUser.setName(userDTO.getName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPassword(userDTO.getPassword());
        existingUser.setRole(userDTO.getRole());

        User updatedUser = userRepository.save(existingUser);

        return UserMapper.toDTO(updatedUser);
    }

    /**
     * Elimina un usuario del sistema.
     *
     * @param id ID del usuario.
     * @throws ResourceNotFoundException si el usuario no existe.
     */
    @Override
    public void deleteUser(Integer id) {

        User existingUser = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No se ha encontrado ningún usuario con el ID: " + id)
        );

        userRepository.delete(existingUser);
    }

    /**
     * Recupera los usuarios que tienen más de X préstamos activos.
     *
     * @param x número mínimo de préstamos activos
     * @return lista de usuarios en formato DTO
     */
    @Override
    public List<UserDTO> getUsersWithMoreThanXActiveLoans(int x) {

        return loanRepository.findUsersWithMoreThanXActiveLoans(x)
                .stream()
                .map(UserMapper::toDTO)
                .toList();
    }

    /**
     * Recupera los usuarios que han realizado más de X préstamos
     * en total (incluyendo préstamos devueltos y activos).
     *
     * <p>
     * Esta operación consulta el repositorio de préstamos y agrupa
     * por usuario, contando todos los préstamos asociados sin filtrar
     * por estado de devolución.
     * </p>
     *
     * @param x número mínimo de préstamos totales que debe haber realizado el usuario
     * @return lista de usuarios en formato DTO que superan ese número de préstamos
     */
    @Override
    public List<UserDTO> getUsersWithMoreThanXTotalLoans(int x) {

        return loanRepository.findUsersWithMoreThanXTotalLoans(x)
                .stream()
                .map(UserMapper::toDTO)
                .toList();
    }
}

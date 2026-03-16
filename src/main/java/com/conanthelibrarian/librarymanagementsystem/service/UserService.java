package com.conanthelibrarian.librarymanagementsystem.service;

import com.conanthelibrarian.librarymanagementsystem.dto.UserDTO;

import java.util.List;

/**
 * Servicio para la gestión de usuarios.
 *
 * <p>
 * Define las operaciones de negocio relacionadas con los usuarios
 * del sistema. Utiliza DTOs para desacoplar la capa de persistencia
 * de la capa de presentación.
 * </p>
 */
public interface UserService {

    /**
     * Obtiene todos los usuarios.
     *
     * @return lista de usuarios en formato DTO
     */
    List<UserDTO> getAllUsers();

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id identificador del usuario
     * @return usuario encontrado en formato DTO
     */
    UserDTO getUserById(Integer id);

    /**
     * Crea un nuevo usuario.
     *
     * @param userDTO datos del usuario a crear
     * @return usuario creado en formato DTO
     */
    UserDTO createUser(UserDTO userDTO);

    /**
     * Actualiza un usuario existente.
     *
     * @param id      identificador del usuario
     * @param userDTO nuevos datos
     * @return usuario actualizado en formato DTO
     */
    UserDTO updateUser(Integer id, UserDTO userDTO);

    /**
     * Elimina un usuario por su ID.
     *
     * @param id identificador del usuario
     */
    void deleteUser(Integer id);

    /**
     * Recupera los usuarios que tienen más de X préstamos activos.
     *
     * @param x número mínimo de préstamos activos
     * @return lista de usuarios que superan ese número
     */
    List<UserDTO> getUsersWithMoreThanXActiveLoans(int x);

    /**
     * Recupera los usuarios que han realizado más de X préstamos
     * en total.
     *
     * @param x número mínimo de préstamos
     * @return lista de usuarios en formato DTO
     */
    List<UserDTO> getUsersWithMoreThanXTotalLoans(int x);

    /**
     * Recupera un usuario a partir de su nombre.
     *
     * @param name nombre del usuario
     * @return usuario encontrado
     * @throws ResourceNotFoundException si el usuario no existe
     */
    UserDTO getUserByName(String name);
}

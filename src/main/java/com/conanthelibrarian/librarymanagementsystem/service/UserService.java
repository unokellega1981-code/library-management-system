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
}

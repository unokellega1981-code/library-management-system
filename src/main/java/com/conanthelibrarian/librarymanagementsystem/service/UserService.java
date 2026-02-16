package com.conanthelibrarian.librarymanagementsystem.service;

import com.conanthelibrarian.librarymanagementsystem.dto.UserDTO;

import java.util.List;

/**
 * Servicio que define las operaciones de negocio relacionadas con usuarios.
 *
 * <p>En este proyecto, los usuarios pueden tener rol MEMBER o STAFF.</p>
 */
public interface UserService {

    /**
     * Obtiene todos los usuarios del sistema.
     *
     * @return lista de usuarios en formato DTO
     */
    List<UserDTO> getAllUsers();

    /**
     * Obtiene un usuario por su identificador.
     *
     * @param id identificador del usuario
     * @return usuario en formato DTO
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
     * @param userDTO datos nuevos del usuario
     * @return usuario actualizado en formato DTO
     */
    UserDTO updateUser(Integer id, UserDTO userDTO);

    /**
     * Elimina un usuario por su identificador.
     *
     * @param id identificador del usuario
     */
    void deleteUser(Integer id);
}

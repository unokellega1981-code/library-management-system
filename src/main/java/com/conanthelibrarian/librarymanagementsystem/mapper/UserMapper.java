package com.conanthelibrarian.librarymanagementsystem.mapper;

import com.conanthelibrarian.librarymanagementsystem.dto.UserDTO;
import com.conanthelibrarian.librarymanagementsystem.entity.User;

/**
 * Clase encargada de convertir entre {@link User} (entidad) y {@link UserDTO} (DTO).
 *
 * <p>Los mappers permiten separar la capa de persistencia (entidades JPA)
 * de la capa de API (DTOs), evitando exponer directamente las entidades en los controllers.</p>
 */
public class UserMapper {

    private UserMapper() {
        // Constructor privado para evitar instanciación.
    }

    /**
     * Convierte una entidad {@link User} en un {@link UserDTO}.
     *
     * @param user entidad User
     * @return DTO equivalente o null si el parámetro es null
     */
    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
    }

    /**
     * Convierte un {@link UserDTO} en una entidad {@link User}.
     *
     * @param userDTO DTO UserDTO
     * @return entidad equivalente o null si el parámetro es null
     */
    public static User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        return new User(
                userDTO.getId(),
                userDTO.getName(),
                userDTO.getEmail(),
                userDTO.getPassword(),
                userDTO.getRole()
        );
    }
}

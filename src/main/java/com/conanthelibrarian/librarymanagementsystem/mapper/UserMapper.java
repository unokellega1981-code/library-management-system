package com.conanthelibrarian.librarymanagementsystem.mapper;

import com.conanthelibrarian.librarymanagementsystem.dto.UserDTO;
import com.conanthelibrarian.librarymanagementsystem.entity.User;

/**
 * Mapper para la entidad User.
 *
 * <p>
 * Convierte entre User y UserDTO y viceversa, manteniendo la separación
 * entre la capa de persistencia y la capa de presentación.
 * </p>
 */
public class UserMapper {

    /**
     * Constructor privado para evitar instanciación
     */
    private UserMapper() {
    }

    /**
     * Convierte User → UserDTO
     */
    public static UserDTO toDTO(User user) {
        if (user == null) return null;

        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
    }

    /**
     * Convierte UserDTO → User
     */
    public static User toEntity(UserDTO userDTO) {
        if (userDTO == null) return null;

        return new User(
                userDTO.getId(),
                userDTO.getName(),
                userDTO.getEmail(),
                userDTO.getPassword(),
                userDTO.getRole()
        );
    }
}

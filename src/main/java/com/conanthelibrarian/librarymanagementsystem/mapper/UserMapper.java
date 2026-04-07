package com.conanthelibrarian.librarymanagementsystem.mapper;

import com.conanthelibrarian.librarymanagementsystem.dto.UserDTO;
import com.conanthelibrarian.librarymanagementsystem.entity.User;
import com.conanthelibrarian.librarymanagementsystem.exception.BadRequestException;

public class UserMapper {

    private UserMapper() {
    }

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

    public static User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            throw new BadRequestException("El cuerpo de la petición no puede ser null");
        }

        if (userDTO.getName() == null || userDTO.getName().isBlank()) {
            throw new BadRequestException("El campo 'name' es obligatorio");
        }

        if (userDTO.getEmail() == null || userDTO.getEmail().isBlank()) {
            throw new BadRequestException("El campo 'email' es obligatorio");
        }

        if (userDTO.getPassword() == null || userDTO.getPassword().isBlank()) {
            throw new BadRequestException("El campo 'password' es obligatorio");
        }

        if (userDTO.getRole() == null) {
            throw new BadRequestException("El campo 'role' es obligatorio");
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

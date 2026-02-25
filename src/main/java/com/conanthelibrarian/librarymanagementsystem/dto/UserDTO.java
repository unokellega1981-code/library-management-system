package com.conanthelibrarian.librarymanagementsystem.dto;

import com.conanthelibrarian.librarymanagementsystem.constants.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para representar un usuario.
 *
 * <p>
 * Este objeto se usa para transferir datos entre la API y el cliente,
 * evitando exponer directamente la entidad User.
 * Incluye validaciones de los campos.
 * </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    /**
     * Identificador único del usuario
     */
    private Integer id;

    /**
     * Nombre completo (no puede estar vacío)
     */
    @NotBlank(message = "Tienes que poner un nombre al usuario")
    private String name;

    /**
     * Email del usuario (no puede estar vacío y debe ser válido)
     */
    @NotBlank(message = "Tienes que poner un email al usuario")
    @Email(message = "El email debe ser correcto")
    private String email;

    /**
     * Contraseña del usuario (no puede estar vacía)
     */
    @NotBlank(message = "Tienes que poner una contraseña")
    private String password;

    /**
     * Rol del usuario (no nulo)
     */
    @NotNull(message = "Tienes que poner que tipo de usuario es")
    private Role role;
}

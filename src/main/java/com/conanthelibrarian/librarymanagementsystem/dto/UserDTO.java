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
 * DTO (Data Transfer Object) que representa un usuario en la capa de API.
 *
 * <p>Se utiliza para enviar y recibir información relacionada con usuarios desde los
 * controladores, evitando exponer directamente la entidad {@code User}.</p>
 *
 * <p>Incluye validaciones básicas para asegurar que los datos de registro y actualización
 * son correctos.</p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    /**
     * Identificador del usuario.
     *
     * <p>En operaciones de creación normalmente será {@code null}.
     * En operaciones de actualización sí puede venir informado.</p>
     */
    private Integer id;

    /**
     * Nombre del usuario.
     */
    @NotBlank(message = "Tienes que poner un nombre al usuario")
    private String name;

    /**
     * Email del usuario.
     */
    @NotBlank(message = "Tienes que poner un email al usuario")
    @Email(message = "El email debe ser correcto")
    private String email;

    /**
     * Contraseña del usuario.
     *
     * <p>En este proyecto se guarda como texto simple (según el SQL).
     * En un sistema real debería guardarse siempre como hash.</p>
     */
    @NotBlank(message = "Tienes que poner una contraseña")
    private String password;

    /**
     * Rol del usuario (MEMBER o STAFF).
     */
    @NotNull(message = "Tienes que poner que tipo de usuario es")
    private Role role;

}

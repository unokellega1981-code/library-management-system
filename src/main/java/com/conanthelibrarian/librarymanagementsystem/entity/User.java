package com.conanthelibrarian.librarymanagementsystem.entity;

import com.conanthelibrarian.librarymanagementsystem.constants.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad que representa un usuario del sistema.
 *
 * <p>
 * Puede ser un miembro (MEMBER) o personal de la biblioteca (STAFF).
 * </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    /**
     * Identificador único del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Nombre completo del usuario.
     */
    @Column(name = "name")
    private String name;

    /**
     * Correo electrónico único.
     */
    @Column(name = "email", unique = true)
    private String email;

    /**
     * Contraseña del usuario.
     */
    @Column(name = "password")
    private String password;

    /**
     * Rol del usuario dentro del sistema.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
}

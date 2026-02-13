package com.conanthelibrarian.librarymanagementsystem.entity;

import com.conanthelibrarian.librarymanagementsystem.constants.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad que representa la tabla "Users" de la base de datos.
 *
 * <p>Un usuario puede ser de dos tipos:</p>
 * <ul>
 *     <li>MEMBER: usuario normal que pide libros prestados</li>
 *     <li>STAFF: personal de la biblioteca</li>
 * </ul>
 *
 * <p>En esta entidad el rol se almacena como texto en la base de datos
 * y se mapea como enum para mantener consistencia en Java.</p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Users")
public class User {

    /**
     * Identificador único del usuario.
     * Se genera automáticamente en base de datos con identity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Nombre del usuario.
     */
    @Column(name = "name")
    private String name;

    /**
     * Email del usuario.
     */
    @Column(name = "email")
    private String email;

    /**
     * Contraseña del usuario.
     *
     * <p>IMPORTANTE: en un sistema real, esto debería almacenarse cifrado (hash),
     * pero para este proyecto se guarda como texto simple según el SQL proporcionado.</p>
     */
    @Column(name = "password")
    private String password;

    /**
     * Rol del usuario (MEMBER o STAFF).
     *
     * <p>Se guarda como texto en base de datos.</p>
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

}

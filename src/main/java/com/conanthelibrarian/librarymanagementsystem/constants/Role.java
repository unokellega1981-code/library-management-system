package com.conanthelibrarian.librarymanagementsystem.constants;

/**
 * Enumerado que representa los roles de usuario dentro del sistema.
 *
 * <p>
 * Existen dos tipos de usuarios:
 * <ul>
 *     <li>MEMBER: Usuario estándar que puede solicitar préstamos.</li>
 *     <li>STAFF: Personal de la biblioteca con permisos de gestión.</li>
 * </ul>
 * </p>
 */
public enum Role {

    /**
     * Usuario estándar del sistema.
     */
    MEMBER,

    /**
     * Personal de la biblioteca.
     */
    STAFF
}

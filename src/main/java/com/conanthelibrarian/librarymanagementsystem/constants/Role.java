package com.conanthelibrarian.librarymanagementsystem.constants;

/**
 * Enum que representa los roles disponibles en el sistema.
 *
 * <p>Según los requisitos del proyecto, únicamente existen dos roles:</p>
 * <ul>
 *     <li>{@link #MEMBER}: usuario normal de la biblioteca (puede pedir libros prestados)</li>
 *     <li>{@link #STAFF}: personal de la biblioteca (puede realizar tareas de gestión)</li>
 * </ul>
 *
 * <p>Normalmente este enum se almacena en base de datos como texto usando
 * {@code @Enumerated(EnumType.STRING)} para evitar problemas si el orden del enum cambia.</p>
 */
public enum Role {
    MEMBER,
    STAFF
}

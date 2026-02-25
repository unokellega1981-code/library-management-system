package com.conanthelibrarian.librarymanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de arranque del sistema de gestión de biblioteca.
 *
 * <p>
 * Esta clase inicia la aplicación Spring Boot y configura automáticamente
 * el contexto de Spring, incluyendo todos los componentes del sistema
 * como controladores, servicios, repositorios y la conexión con la base
 * de datos PostgreSQL.
 * </p>
 *
 * <p>
 * El sistema permite gestionar:
 * <ul>
 *     <li>Libros</li>
 *     <li>Usuarios</li>
 *     <li>Préstamos</li>
 * </ul>
 * </p>
 *
 * @author Juan Antonio Carrera González
 */
@SpringBootApplication
public class LibraryManagementSystemApplication {

    /**
     * Método principal que actúa como punto de entrada de la aplicación.
     *
     * @param args argumentos recibidos por línea de comandos
     */
    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementSystemApplication.class, args);
    }
}

package com.conanthelibrarian.librarymanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada principal de la aplicación Library Management System.
 *
 * <p>Esta clase se encarga de arrancar Spring Boot y levantar el contexto de Spring,
 * además del servidor embebido (Tomcat por defecto).</p>
 *
 * <p>La anotación {@link SpringBootApplication} activa:</p>
 * <ul>
 *     <li>El auto-configurado de Spring Boot</li>
 *     <li>El escaneo de componentes (component scanning) a partir de este paquete</li>
 *     <li>La configuración de Spring para trabajar con anotaciones</li>
 * </ul>
 *
 * <p>En un proyecto real, esta clase debe mantenerse lo más simple posible y no debería
 * contener lógica de negocio.</p>
 */
@SpringBootApplication
public class LibraryManagementSystemApplication {

    /**
     * Método principal que arranca la aplicación.
     *
     * @param args argumentos de arranque de la aplicación
     */
    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementSystemApplication.class, args);
    }
}

package com.conanthelibrarian.librarymanagementsystem.repository;

import com.conanthelibrarian.librarymanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio JPA para la entidad User.
 *
 * <p>
 * Proporciona métodos para realizar operaciones CRUD y consultas
 * sobre usuarios de la biblioteca.
 * </p>
 *
 * <p>
 * Métodos disponibles por defecto:
 * <ul>
 *     <li>save()</li>
 *     <li>findById()</li>
 *     <li>findAll()</li>
 *     <li>delete()</li>
 * </ul>
 * </p>
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Busca un usuario por su nombre.
     *
     * <p>
     * Devuelve un Optional para manejar correctamente
     * el caso en el que el usuario no exista.
     * </p>
     *
     * @param name nombre del usuario
     * @return usuario encontrado
     */
    Optional<User> findByName(String name);
}

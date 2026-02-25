package com.conanthelibrarian.librarymanagementsystem.repository;

import com.conanthelibrarian.librarymanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

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
}

package com.conanthelibrarian.librarymanagementsystem.repository;

import com.conanthelibrarian.librarymanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio de acceso a datos para la entidad {@link User}.
 *
 * <p>Esta interfaz extiende {@link JpaRepository}, por lo que Spring Data JPA
 * genera automáticamente la implementación en tiempo de ejecución.</p>
 *
 * <p>Permite realizar las operaciones CRUD básicas sobre usuarios, como:</p>
 * <ul>
 *     <li>Crear usuarios</li>
 *     <li>Actualizar usuarios</li>
 *     <li>Buscar usuarios por ID</li>
 *     <li>Listar usuarios</li>
 *     <li>Eliminar usuarios</li>
 * </ul>
 *
 * <p>Más adelante se pueden añadir métodos adicionales para búsquedas específicas,
 * por ejemplo buscar usuarios por nombre o email.</p>
 */
public interface UserRepository extends JpaRepository<User, Integer> {
}

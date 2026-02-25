package com.conanthelibrarian.librarymanagementsystem.repository;

import com.conanthelibrarian.librarymanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

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
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Recupera todos los usuarios que tienen más de una cantidad
     * determinada de préstamos activos.
     *
     * <p>Se consideran préstamos activos aquellos cuya fecha de devolución
     * (dueDate) es null.</p>
     *
     * <p>La consulta agrupa por usuario y filtra mediante HAVING.</p>
     *
     * @param minLoans número mínimo de préstamos activos que debe tener el usuario
     * @return lista de {@link User} que cumplen la condición
     */
    @Query("""
       SELECT u
       FROM User u
       JOIN Loan l ON l.user = u
       WHERE l.dueDate IS NULL
       GROUP BY u
       HAVING COUNT(l) >= :minLoans
       """)
    List<User> findUsersWithMoreThanXActiveLoans(Integer minLoans);
}

package com.conanthelibrarian.librarymanagementsystem.repository;

import com.conanthelibrarian.librarymanagementsystem.entity.Loan;
import com.conanthelibrarian.librarymanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Integer> {

    List<Loan> findBookByReturnedDateIsNull();

    @Query("""
            SELECT l.user
            FROM Loan l
            WHERE l.returnedDate IS NULL
            GROUP BY l.user
            HAVING COUNT(l) >= :x
            """)
    List<User> findUsersWithMoreThanXActiveLoans(@Param("x") int x);

    // Esta búsqueda no la pides pero la hago porque me parece más natural, ya que mira cuantos clientes tienen más de X
    // préstamos en general, tanto devueltos como activos.

    @Query("""
            SELECT l.user
            FROM Loan l
            GROUP BY l.user
            HAVING COUNT(l) >= :x
            """)
    List<User> findUsersWithMoreThanXTotalLoans(@Param("x") int x);

    List<Loan> findByUserId(Integer userId);
}

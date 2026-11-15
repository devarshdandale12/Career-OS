package com.devarsh.careeros.repository;

import com.devarsh.careeros.entity.Role;
import com.devarsh.careeros.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Login, and "is this email already registered?" during sign-up
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    // Changing your email later: it must not clash with SOMEONE ELSE's email
    boolean existsByEmailAndIdNot(String email, Long id);

    // Admin: search by name or email, one page at a time
    @Query("""
            select u from User u
            where lower(u.fullName) like lower(concat('%', :keyword, '%'))
               or lower(u.email) like lower(concat('%', :keyword, '%'))
            """)
    Page<User> search(@Param("keyword") String keyword, Pageable pageable);

    // Admin: filter the list
    Page<User> findByRole(Role role, Pageable pageable);

    Page<User> findByEnabled(boolean enabled, Pageable pageable);

    // Admin dashboard numbers
    long countByRole(Role role);

    long countByEnabled(boolean enabled);

    long countByCreatedAtAfter(LocalDateTime since);
}
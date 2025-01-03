package br.com.nextgen.DGA_DB_MANAGER.repositories.user;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.nextgen.DGA_DB_MANAGER.domain.user.User;

public interface UserRepository extends JpaRepository<User,BigInteger> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM users u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :text, '%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%', :text, '%'))")
    Page<User> findByNameContainingIgnoreCase(@Param("text") String text, Pageable pageable);
}
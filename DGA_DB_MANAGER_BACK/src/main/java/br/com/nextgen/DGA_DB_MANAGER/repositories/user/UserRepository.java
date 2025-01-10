package br.com.nextgen.DGA_DB_MANAGER.repositories.user;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.nextgen.DGA_DB_MANAGER.domain.user.User;

public interface UserRepository extends JpaRepository<User,BigInteger> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM users u JOIN u.accounts a WHERE a.id = :accountId ")
    List<User> findByUserAccount(@Param("accountId") BigInteger accountId);
    //@Query("SELECT u FROM users u JOIN account_user a WHERE a.id_account = :accountId AND  ( LOWER(u.name) LIKE LOWER(CONCAT('%', :text, '%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%', :text, '%')))")
    // @Query(value = "SELECT u.* FROM users u INNER JOIN account_user a ON a.id_user = u.id WHERE a.id_account = :accountId AND (LOWER(u.name) LIKE LOWER(CONCAT('%', :text, '%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%', :text, '%')))", 
    //    countQuery = "SELECT COUNT(*) FROM users u INNER JOIN account_user a ON a.id_user = u.id WHERE a.id_account = :accountId AND (LOWER(u.name) LIKE LOWER(CONCAT('%', :text, '%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%', :text, '%')))", 
    //    nativeQuery = true)
    // Page<User> findByNameContainingIgnoreCase(@Param("text") String text, Pageable pageable,@Param("accountId") BigInteger accountId);

    @Query("SELECT u FROM users u JOIN u.accounts a WHERE a.id = :accountId AND  (LOWER(u.name) LIKE LOWER(CONCAT('%', :text, '%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%', :text, '%')))")
    Page<User> findByNameContainingIgnoreCase(@Param("text") String text, Pageable pageable,@Param("accountId") BigInteger accountId);
}
package br.com.nextgen.DGA_DB_MANAGER.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nextgen.DGA_DB_MANAGER.domain.user.User;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByEmail(String email);
}

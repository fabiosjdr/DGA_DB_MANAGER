package br.com.nextgen.DGA_DB_MANAGER.repositories.user_roles;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nextgen.DGA_DB_MANAGER.domain.user_roles.UserRoles;

public interface UserRolesRepository extends JpaRepository<UserRoles,String> {

    Optional<UserRoles> findByName(String name);
}


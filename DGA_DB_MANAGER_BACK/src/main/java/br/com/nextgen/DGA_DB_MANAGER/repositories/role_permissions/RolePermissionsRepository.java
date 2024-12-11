package br.com.nextgen.DGA_DB_MANAGER.repositories.role_permissions;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nextgen.DGA_DB_MANAGER.domain.role_permissions.RolePermissions;

public interface RolePermissionsRepository extends JpaRepository<RolePermissions,BigInteger> {
    // Optional<Account> findByEmail(String email);
 }
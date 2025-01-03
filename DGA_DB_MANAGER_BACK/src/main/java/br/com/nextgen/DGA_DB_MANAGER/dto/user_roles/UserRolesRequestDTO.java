package br.com.nextgen.DGA_DB_MANAGER.dto.user_roles;

import java.math.BigInteger;

import jakarta.validation.constraints.NotEmpty;

public record UserRolesRequestDTO(
    BigInteger id, 
    @NotEmpty String name
) {}

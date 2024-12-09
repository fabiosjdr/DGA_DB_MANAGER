package br.com.nextgen.DGA_DB_MANAGER.dto.user;

import java.math.BigInteger;

import jakarta.validation.constraints.NotEmpty;

public record UserRequestDTO(
              BigInteger id, 
    @NotEmpty String     name, 
    @NotEmpty String     email,
              String     password,
              Boolean    active,
              BigInteger id_role
) {}

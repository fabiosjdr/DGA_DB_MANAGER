package br.com.nextgen.DGA_DB_MANAGER.dto.user;

import jakarta.validation.constraints.NotEmpty;

public record UserRequestDTO(String id, @NotEmpty String name, @NotEmpty String email, @NotEmpty String password) {}

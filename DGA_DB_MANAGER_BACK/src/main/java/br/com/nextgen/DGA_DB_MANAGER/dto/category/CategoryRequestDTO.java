package br.com.nextgen.DGA_DB_MANAGER.dto.category;

import jakarta.validation.constraints.NotEmpty;

public record CategoryRequestDTO( @NotEmpty String name, String id) {}




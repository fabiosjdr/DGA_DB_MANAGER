package br.com.nextgen.DGA_DB_MANAGER.dto.status;
import jakarta.validation.constraints.NotEmpty;

public record StatusRequestDTO(@NotEmpty String name, String id, Boolean timer) {}

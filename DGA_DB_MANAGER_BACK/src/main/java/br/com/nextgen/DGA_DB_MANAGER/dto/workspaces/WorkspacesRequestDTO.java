package br.com.nextgen.DGA_DB_MANAGER.dto.workspaces;

import java.math.BigInteger;

import jakarta.validation.constraints.NotEmpty;

public record WorkspacesRequestDTO(

              BigInteger  id,
    @NotEmpty String      name 
) {}

package br.com.nextgen.DGA_DB_MANAGER.dto.menu;

import java.math.BigInteger;

import jakarta.validation.constraints.NotEmpty;

public record MenuRequestDTO(

              BigInteger id,
    @NotEmpty String     name, 
    @NotEmpty String     path, 
              Boolean    active,
              String     icon
) {}

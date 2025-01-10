package br.com.nextgen.DGA_DB_MANAGER.dto.category;

import java.math.BigInteger;

import jakarta.validation.constraints.NotEmpty;

public record CategoryRequestDTO( @NotEmpty String name, String id,BigInteger id_account) {}




package br.com.nextgen.DGA_DB_MANAGER.dto.client;

import java.math.BigInteger;

import jakarta.validation.constraints.NotEmpty;

public record ClientRequestDTO( 
            BigInteger id, 
  @NotEmpty String name, 
            String responsable,
            String telephone, 
            Integer hours,
            BigInteger id_account
){}

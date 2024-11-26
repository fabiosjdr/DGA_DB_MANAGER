package br.com.nextgen.DGA_DB_MANAGER.dto.project;

import java.math.BigInteger;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotEmpty;

public record ProjectsRequestDTO(
              BigInteger id,
    @NotEmpty String name, 
    @NonNull  BigInteger id_client, 
              String description, 
              String start_date, 
              String end_date,
              BigInteger id_account
) {
    
}

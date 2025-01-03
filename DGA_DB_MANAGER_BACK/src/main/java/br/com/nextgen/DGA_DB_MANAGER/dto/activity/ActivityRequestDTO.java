package br.com.nextgen.DGA_DB_MANAGER.dto.activity;

import java.math.BigInteger;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotEmpty;

public record ActivityRequestDTO( 
              BigInteger id, 
    @NotEmpty String activity, 
    @NotEmpty String start_date,
    @NotEmpty String end_date,
    @NonNull  BigInteger id_client,
    @NonNull  BigInteger id_category,
    @NonNull  BigInteger id_project,
              BigInteger id_user,
              BigInteger id_account
){}

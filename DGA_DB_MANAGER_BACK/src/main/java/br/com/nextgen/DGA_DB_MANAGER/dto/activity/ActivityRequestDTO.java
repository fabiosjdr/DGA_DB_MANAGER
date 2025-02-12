package br.com.nextgen.DGA_DB_MANAGER.dto.activity;

import java.math.BigInteger;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotEmpty;

public record ActivityRequestDTO( 
              BigInteger id, 
    @NotEmpty String activity, 
    @NotEmpty String start_date,
    @NotEmpty String end_date,
    @NonNull  BigInteger id_category,
    @NonNull  BigInteger id_team,
    @NonNull  BigInteger id_workspaces,
              BigInteger id_user,
              BigInteger id_account
){}

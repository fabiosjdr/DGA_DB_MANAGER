package br.com.nextgen.DGA_DB_MANAGER.dto.activity_detail;

import java.math.BigInteger;

import jakarta.validation.constraints.NotNull;

public record ActivityDetailRequestDTO(

              BigInteger    id, 
    @NotNull  BigInteger    id_activity,
              BigInteger    id_stage,
              BigInteger    old_id_stage,
              String        title, 
              BigInteger    id_user,
              String        description,
              String        priority,
              String        color,
              String        start_date,
              String        due_date,
              Integer       progress
){}

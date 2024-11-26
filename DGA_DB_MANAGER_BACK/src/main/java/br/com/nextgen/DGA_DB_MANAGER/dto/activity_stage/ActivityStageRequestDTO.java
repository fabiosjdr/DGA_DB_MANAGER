package br.com.nextgen.DGA_DB_MANAGER.dto.activity_stage;

import java.math.BigInteger;

import jakarta.validation.constraints.NotNull;

public record ActivityStageRequestDTO(

              BigInteger  id, 
    @NotNull  BigInteger  id_activity,
              String      name,
              Boolean     timer
           
){}

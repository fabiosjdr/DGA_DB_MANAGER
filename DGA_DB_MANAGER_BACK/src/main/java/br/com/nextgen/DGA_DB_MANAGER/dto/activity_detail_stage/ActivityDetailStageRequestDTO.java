package br.com.nextgen.DGA_DB_MANAGER.dto.activity_detail_stage;

import java.math.BigInteger;

import jakarta.validation.constraints.NotNull;

public record ActivityDetailStageRequestDTO(

              BigInteger  id, 
    @NotNull  BigInteger  id_activity_detail,
    @NotNull  BigInteger  id_activity_stage,
              String      start_date,
              String      end_date

){}

            

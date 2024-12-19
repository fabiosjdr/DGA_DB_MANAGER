package br.com.nextgen.DGA_DB_MANAGER.dto.report_activity;

import java.math.BigInteger;

public record ReportActivityResponseDTO(

   
    BigInteger    id_activity_detail,
    BigInteger    id_activity,
    BigInteger    id_stage,
    String        title,
    String        stage_name,
    BigInteger    id_user,
    String        user_name,
    Long          duration
    
) {}

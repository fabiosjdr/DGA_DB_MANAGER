package br.com.nextgen.DGA_DB_MANAGER.dto.activity_detail_stage;

import java.math.BigInteger;
import java.time.LocalDateTime;

public record ActivityDetailStageResponseDTO(

      BigInteger    id, 
      BigInteger    id_activity_detail,
      BigInteger    id_activity_stage,
      LocalDateTime start_date,
      LocalDateTime end_date

) {

    public ActivityDetailStageResponseDTO() {
        this(
            null,
            null, 
            null,
            null,
            null
        ); // Construtor vazio para tornar todos os campos opcionais
    }
}

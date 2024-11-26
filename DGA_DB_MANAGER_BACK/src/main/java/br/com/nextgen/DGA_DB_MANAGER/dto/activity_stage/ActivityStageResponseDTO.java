package br.com.nextgen.DGA_DB_MANAGER.dto.activity_stage;

import java.math.BigInteger;

import br.com.nextgen.DGA_DB_MANAGER.domain.activity.Activity;

public record ActivityStageResponseDTO(

    BigInteger  id, 
    Activity    id_activity,
    String      name,
    Boolean     timer

) {

    public ActivityStageResponseDTO() {
        this(
            null,
            null, 
            null,
            null
        ); // Construtor vazio para tornar todos os campos opcionais
    }
}

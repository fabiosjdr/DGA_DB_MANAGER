package br.com.nextgen.DGA_DB_MANAGER.dto.activity_detail;

import java.math.BigInteger;
import java.time.LocalDateTime;

import org.apache.catalina.User;

import br.com.nextgen.DGA_DB_MANAGER.domain.activity.Activity;
import br.com.nextgen.DGA_DB_MANAGER.domain.activity_stage.ActivityStage;

public record ActivityDetailResponseDTO(

    BigInteger    id, 
    Activity      activity,
    ActivityStage activity_stage,
    String        title, 
    User          user,
    String        description,
    LocalDateTime start_date,
    LocalDateTime due_date,
    String        priority,
    String        color,
    Integer       progress
) {

    public ActivityDetailResponseDTO() {
        this(
            null,
            null, 
            null, 
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        ); // Construtor vazio para tornar todos os campos opcionais
    }
}

package br.com.nextgen.DGA_DB_MANAGER.dto.activity;

import java.math.BigInteger;
import java.time.LocalDateTime;

import br.com.nextgen.DGA_DB_MANAGER.domain.account.Account;
import br.com.nextgen.DGA_DB_MANAGER.domain.category.Category;
import br.com.nextgen.DGA_DB_MANAGER.domain.client.Client;
import br.com.nextgen.DGA_DB_MANAGER.domain.project.Project;
import br.com.nextgen.DGA_DB_MANAGER.domain.user.User;

public record ActivityResponseDTO(
    BigInteger     id,
    String         activity,
    Client         client, 
    Category       category,
    Project        project, 
    LocalDateTime  start_date, 
    LocalDateTime  end_date,
    User           user,
    Account        account
    ) {
        
    public ActivityResponseDTO() {
        this(
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

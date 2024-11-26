package br.com.nextgen.DGA_DB_MANAGER.dto.project;

import java.math.BigInteger;

import br.com.nextgen.DGA_DB_MANAGER.domain.account.Account;
import br.com.nextgen.DGA_DB_MANAGER.domain.client.Client;

public record ProjectResponseDTO(BigInteger id,String name,Client client, String description, String start_date, String end_date,Account account) {
    public ProjectResponseDTO() {
        this(
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
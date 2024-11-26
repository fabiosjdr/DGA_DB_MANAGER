package br.com.nextgen.DGA_DB_MANAGER.dto.client;

import java.math.BigInteger;

import br.com.nextgen.DGA_DB_MANAGER.domain.account.Account;


public record ClientResponseDTO(
        BigInteger id, 
        String name, 
        String responsable,
        String telephone, 
        Integer hours,
        Account account
    ) {
    public ClientResponseDTO() {
        this(
            null,
            null,
            null, 
            null, 
            null,
            null
            ); // Construtor vazio para tornar todos os campos opcionais
    }
}
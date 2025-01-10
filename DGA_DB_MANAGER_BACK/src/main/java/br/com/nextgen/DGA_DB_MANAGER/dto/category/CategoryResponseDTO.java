package br.com.nextgen.DGA_DB_MANAGER.dto.category;

import java.math.BigInteger;

import br.com.nextgen.DGA_DB_MANAGER.domain.account.Account;


public record CategoryResponseDTO(BigInteger id, String name,Account account) {
    public CategoryResponseDTO() {
        this(null,null,null); // Construtor vazio para tornar todos os campos opcionais
    }
}
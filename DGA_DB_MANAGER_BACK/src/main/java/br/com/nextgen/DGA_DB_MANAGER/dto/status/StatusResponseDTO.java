package br.com.nextgen.DGA_DB_MANAGER.dto.status;

import java.math.BigInteger;

import br.com.nextgen.DGA_DB_MANAGER.domain.account.Account;

public record StatusResponseDTO(BigInteger id, String name,Boolean timer,Account account) {
    public StatusResponseDTO() {
       this(null,null,null,null); // Construtor vazio para tornar todos os campos opcionais
    }
}
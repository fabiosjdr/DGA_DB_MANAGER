package br.com.nextgen.DGA_DB_MANAGER.dto.teams;

import java.math.BigInteger;

import br.com.nextgen.DGA_DB_MANAGER.domain.account.Account;

public record TeamsResponseDTO(
    BigInteger id, 
    String     title,
    String     description,
    Account    account) {
        public TeamsResponseDTO() {
            this(
                     null,
                 null,
            null,
                null
            ); // Construtor vazio para tornar todos os campos opcionais
        }
    }


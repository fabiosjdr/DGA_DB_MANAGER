package br.com.nextgen.DGA_DB_MANAGER.dto.teams;

import java.math.BigInteger;

import br.com.nextgen.DGA_DB_MANAGER.domain.account.Account;
import br.com.nextgen.DGA_DB_MANAGER.domain.workspaces.Workspaces;

public record TeamsResponseDTO(
    BigInteger id, 
    String     title,
    String     description,
    Account    account,
    Workspaces workspaces) {
        public TeamsResponseDTO() {
            this(
                     null,
                 null,
            null,
                null,
                null
            ); // Construtor vazio para tornar todos os campos opcionais
        }
    }


package br.com.nextgen.DGA_DB_MANAGER.dto.workspaces;

import java.math.BigInteger;

import br.com.nextgen.DGA_DB_MANAGER.domain.account.Account;

public record WorkspacesResponseDTO(

    BigInteger id, 
    String     name,
    Account    account

){}

package br.com.nextgen.DGA_DB_MANAGER.dto.user;

import java.math.BigInteger;
import java.util.List;

import br.com.nextgen.DGA_DB_MANAGER.domain.account.Account;
import br.com.nextgen.DGA_DB_MANAGER.domain.user_roles.UserRoles;

public record UserResponseDTO(
    BigInteger id, 
    String     name,
    String     email, 
    Boolean    active,
    UserRoles  roles,
    List<Account> accounts
) {

    public UserResponseDTO(){

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



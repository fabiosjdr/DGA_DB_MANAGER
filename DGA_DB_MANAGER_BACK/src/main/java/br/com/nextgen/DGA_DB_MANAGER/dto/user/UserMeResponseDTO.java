package br.com.nextgen.DGA_DB_MANAGER.dto.user;

import java.math.BigInteger;

import br.com.nextgen.DGA_DB_MANAGER.domain.user_roles.UserRoles;

public record UserMeResponseDTO(
    BigInteger id, 
    String     name,
    String     email, 
    Boolean    active,
    UserRoles  roles
) {

    public UserMeResponseDTO(){

        this(
            null,
            null,
            null,
            null,
            null
        ); // Construtor vazio para tornar todos os campos opcionais
    }
}



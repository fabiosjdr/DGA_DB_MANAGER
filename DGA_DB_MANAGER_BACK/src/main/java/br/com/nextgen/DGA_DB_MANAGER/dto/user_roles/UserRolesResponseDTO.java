package br.com.nextgen.DGA_DB_MANAGER.dto.user_roles;

import java.math.BigInteger;

public record UserRolesResponseDTO(BigInteger id, String name) {

    public UserRolesResponseDTO() {
        this(null,null); // Construtor vazio para tornar todos os campos opcionais
    }
}

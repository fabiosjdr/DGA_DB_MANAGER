package br.com.nextgen.DGA_DB_MANAGER.dto.status;

import java.math.BigInteger;

public record StatusResponseDTO(BigInteger id, String name) {
    public StatusResponseDTO() {
        this(null,null); // Construtor vazio para tornar todos os campos opcionais
    }
}
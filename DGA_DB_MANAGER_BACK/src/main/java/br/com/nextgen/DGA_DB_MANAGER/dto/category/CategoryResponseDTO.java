package br.com.nextgen.DGA_DB_MANAGER.dto.category;

import java.math.BigInteger;


public record CategoryResponseDTO(BigInteger id, String name) {
    public CategoryResponseDTO() {
        this(null,null); // Construtor vazio para tornar todos os campos opcionais
    }
}
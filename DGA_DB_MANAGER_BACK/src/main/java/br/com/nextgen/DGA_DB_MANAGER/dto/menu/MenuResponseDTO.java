package br.com.nextgen.DGA_DB_MANAGER.dto.menu;

import java.math.BigInteger;

public record MenuResponseDTO(BigInteger id,String name,String path,Boolean active,String icon) {
    public MenuResponseDTO() {
        this(
            null,
            null,
            null, 
            null,
            null
        ); // Construtor vazio para tornar todos os campos opcionais
    }
}

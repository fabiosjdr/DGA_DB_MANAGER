package br.com.nextgen.DGA_DB_MANAGER.dto.teams;
import java.math.BigInteger;

import jakarta.validation.constraints.NotEmpty;

public record TeamsRequestDTO(
    
              BigInteger  id,
              BigInteger  id_workspaces,
    @NotEmpty String      title,  
              String      description
) {}

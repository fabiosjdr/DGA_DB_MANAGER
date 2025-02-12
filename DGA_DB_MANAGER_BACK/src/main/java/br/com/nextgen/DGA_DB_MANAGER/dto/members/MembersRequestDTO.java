package br.com.nextgen.DGA_DB_MANAGER.dto.members;

import java.math.BigInteger;

import jakarta.validation.constraints.NotNull;

public record MembersRequestDTO(

            BigInteger    id, 
@NotNull    BigInteger    id_user,
@NotNull    BigInteger    id_team,
            Boolean       active
) {}

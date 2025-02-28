package br.com.nextgen.DGA_DB_MANAGER.dto.members;

import java.math.BigInteger;

public record MembersGetRequestDTO(
    BigInteger    id_user,
    BigInteger    id_team
) {}

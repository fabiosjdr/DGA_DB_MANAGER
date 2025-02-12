package br.com.nextgen.DGA_DB_MANAGER.dto.members;

import java.math.BigInteger;

import br.com.nextgen.DGA_DB_MANAGER.domain.teams.Teams;
import br.com.nextgen.DGA_DB_MANAGER.domain.user.User;

public record MembersResponseDTO(

    BigInteger    id, 
    User          user,
    Teams         teams,
    Boolean       active

){}

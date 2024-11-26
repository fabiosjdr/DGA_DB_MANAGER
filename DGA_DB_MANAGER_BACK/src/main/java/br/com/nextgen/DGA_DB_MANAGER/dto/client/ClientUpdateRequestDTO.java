package br.com.nextgen.DGA_DB_MANAGER.dto.client;

import jakarta.validation.constraints.NotEmpty;

public record ClientUpdateRequestDTO( @NotEmpty String id, String name, String responsable,String telephone, String hours){}

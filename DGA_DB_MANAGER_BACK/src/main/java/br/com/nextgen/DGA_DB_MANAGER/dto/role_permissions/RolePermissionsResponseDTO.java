package br.com.nextgen.DGA_DB_MANAGER.dto.role_permissions;

import java.math.BigInteger;

public record RolePermissionsResponseDTO(
    BigInteger id, 
    BigInteger id_role,
    BigInteger id_menu,
    Boolean    allow_edit,
    Boolean    allow_save,
    Boolean    allow_delete
) {

}

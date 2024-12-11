package br.com.nextgen.DGA_DB_MANAGER.domain.role_permissions;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.nextgen.DGA_DB_MANAGER.domain.menu.Menu;
import br.com.nextgen.DGA_DB_MANAGER.domain.user_roles.UserRoles;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Entity(name = "role_permissions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class RolePermissions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private Boolean allow_edit;
    private Boolean allow_save;
    private Boolean allow_delete;

    @ManyToOne
    @JoinColumn(name = "id_menu") 
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "id_role") 
    @JsonIgnore // Ignora a serialização para evitar repetição
    private UserRoles role;

}

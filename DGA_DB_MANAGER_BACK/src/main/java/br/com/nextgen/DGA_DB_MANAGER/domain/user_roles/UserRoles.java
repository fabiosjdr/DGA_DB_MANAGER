package br.com.nextgen.DGA_DB_MANAGER.domain.user_roles;

import java.math.BigInteger;
import java.util.List;

import br.com.nextgen.DGA_DB_MANAGER.domain.role_permissions.RolePermissions;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Entity(name = "user_roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private String     name;

    // @ManyToOne
    // @JoinColumn(name = "id_role") 
    // private RolePermissions permissions;

    // @OneToMany
    // @JoinColumn(name = "id_role")
    // private RolePermissions permissions;

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    
    private List<RolePermissions> permissions;
}

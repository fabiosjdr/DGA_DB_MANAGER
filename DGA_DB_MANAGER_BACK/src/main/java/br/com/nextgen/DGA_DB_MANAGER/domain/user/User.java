package br.com.nextgen.DGA_DB_MANAGER.domain.user;

import java.math.BigInteger;

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
@Entity(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private String     name;
    private String     email;
    private String     password;
    private Boolean    active;
    
    @ManyToOne
    @JoinColumn(name = "id_role") 
    private UserRoles roles;
}

package br.com.nextgen.DGA_DB_MANAGER.domain.user;

import java.math.BigInteger;
import java.util.List;

import br.com.nextgen.DGA_DB_MANAGER.domain.account.Account;
import br.com.nextgen.DGA_DB_MANAGER.domain.user_roles.UserRoles;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<AccountUser> accountUsers;

    @OneToMany
    @JoinTable(
        name = "account_user", // Tabela intermediária
        joinColumns = @JoinColumn(name = "id_user"), // Chave estrangeira de User
        inverseJoinColumns = @JoinColumn(name = "id_account") // Chave estrangeira de Account
    )
    private List<Account> accounts;

    // @OneToOne
    // @JoinColumn(name = "id", referencedColumnName = "id_user") // Ajuste do nome da coluna e da referência
    // private AccountUser account;
}

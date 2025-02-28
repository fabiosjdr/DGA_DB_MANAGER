package br.com.nextgen.DGA_DB_MANAGER.domain.teams;

import java.math.BigInteger;

import br.com.nextgen.DGA_DB_MANAGER.domain.account.Account;
import br.com.nextgen.DGA_DB_MANAGER.domain.workspaces.Workspaces;
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
@Entity(name = "teams")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Teams {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_account") 
    private Account account;

    @ManyToOne
    @JoinColumn(name = "id_workspaces") 
    private Workspaces workspaces;

    // @OneToMany
    // @JoinColumn(name = "id_team") 
    // private List<Members> members;
  
}

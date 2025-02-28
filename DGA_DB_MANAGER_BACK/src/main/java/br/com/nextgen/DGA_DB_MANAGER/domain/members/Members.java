package br.com.nextgen.DGA_DB_MANAGER.domain.members;

import java.math.BigInteger;

import br.com.nextgen.DGA_DB_MANAGER.domain.teams.Teams;
import br.com.nextgen.DGA_DB_MANAGER.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Entity(name = "team_members")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Members {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private Boolean    active;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user") 
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_team")
    private Teams teams;

    
}


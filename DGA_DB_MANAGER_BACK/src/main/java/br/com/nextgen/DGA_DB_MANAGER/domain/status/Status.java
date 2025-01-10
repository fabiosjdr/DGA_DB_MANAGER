package br.com.nextgen.DGA_DB_MANAGER.domain.status;

import java.math.BigInteger;

import br.com.nextgen.DGA_DB_MANAGER.domain.account.Account;
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
@Entity(name = "status")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private String name;
    private Boolean timer;

    @ManyToOne
    @JoinColumn(name = "id_account") 
    private Account account;
}

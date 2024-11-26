package br.com.nextgen.DGA_DB_MANAGER.domain.client;

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
@Entity(name = "client")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private String name;
    private String responsable;
    private String telephone;
    private Integer hours;
    //private BigInteger id_account;

    @ManyToOne
    @JoinColumn(name = "id_account") 
    private Account account;
}
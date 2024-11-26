package br.com.nextgen.DGA_DB_MANAGER.domain.project;

import java.math.BigInteger;

import br.com.nextgen.DGA_DB_MANAGER.domain.account.Account;
import br.com.nextgen.DGA_DB_MANAGER.domain.client.Client;
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
@Entity(name = "projects")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private String name;
    //private BigInteger id_client; //apontado como chave estrangeira abaixo
    private String description;
    private String start_date;
    private String end_date;
    

    @ManyToOne
    @JoinColumn(name = "id_client") // Define que client_id será a chave estrangeira
    private Client client;

    @ManyToOne
    @JoinColumn(name = "id_account") 
    private Account account;
}

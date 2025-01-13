package br.com.nextgen.DGA_DB_MANAGER.domain.activity;

import java.math.BigInteger;
import java.time.LocalDateTime;

import br.com.nextgen.DGA_DB_MANAGER.domain.account.Account;
import br.com.nextgen.DGA_DB_MANAGER.domain.category.Category;
import br.com.nextgen.DGA_DB_MANAGER.domain.client.Client;
import br.com.nextgen.DGA_DB_MANAGER.domain.project.Project;
import br.com.nextgen.DGA_DB_MANAGER.domain.user.User;
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
@Entity(name = "activities")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private String activity;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    //private BigInteger id_category;
    //private BigInteger id_client;
    //private BigInteger id_project;
    //private BigInteger id_status;
    //private BigInteger id_user;
    //private BigInteger id_account;

    @ManyToOne
    @JoinColumn(name = "id_category") 
    private Category category;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "id_project")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "id_user") 
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_account") 
    private Account account;
}

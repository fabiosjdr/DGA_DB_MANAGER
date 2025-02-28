package br.com.nextgen.DGA_DB_MANAGER.domain.activity_detail_comments;

import java.math.BigInteger;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Entity(name = "activity_detail_comment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDetailComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String          id;
    private String        text;
    private LocalDateTime time;

    @Column(name = "id_activity_detail") // Garante que o nome na tabela continue o mesmo
    private BigInteger idActivityDetail;

    @Column(name = "id_user") // Garante que o nome na tabela continue o mesmo
    private BigInteger idUser;
    
    // @OneToOne
    // @JoinColumn(name = "id_activity_detail") 
    // private ActivityDetail detail;

    // @OneToOne
    // @JoinColumn(name = "id_user") 
    // private User user;

    // @ManyToOne
    // @JoinColumn(name = "id_activity_detail") // Chave estrangeira para ActivityDetail
    // private ActivityDetail activityDetail;
}

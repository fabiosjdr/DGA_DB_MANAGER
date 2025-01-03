package br.com.nextgen.DGA_DB_MANAGER.domain.activity_detail;

import java.math.BigInteger;
import java.time.LocalDateTime;

import br.com.nextgen.DGA_DB_MANAGER.domain.activity.Activity;
import br.com.nextgen.DGA_DB_MANAGER.domain.activity_stage.ActivityStage;
import br.com.nextgen.DGA_DB_MANAGER.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Table
@Entity(name = "activity_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDetail {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private String     title;
    private String     description;
    private String     priority;
    private String     color;
    private Integer    progress;
    private LocalDateTime start_date;
    private LocalDateTime due_date;
    
    @OneToOne
    @JoinColumn(name = "id_activity") 
    private Activity activity;

    @OneToOne
    @JoinColumn(name = "id_user") 
    private User user;

    @OneToOne
    @JoinColumn(name = "id_stage") 
    private ActivityStage stage;

}

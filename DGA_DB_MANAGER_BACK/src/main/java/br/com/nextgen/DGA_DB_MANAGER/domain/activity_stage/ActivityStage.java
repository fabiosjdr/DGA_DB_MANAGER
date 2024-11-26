package br.com.nextgen.DGA_DB_MANAGER.domain.activity_stage;

import java.math.BigInteger;

import br.com.nextgen.DGA_DB_MANAGER.domain.activity.Activity;
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
@Entity(name = "activity_stages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityStage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private String  name;
    private Boolean timer;

    @OneToOne
    @JoinColumn(name = "id_activity") 
    private Activity activity;

   
}

package br.com.nextgen.DGA_DB_MANAGER.domain.activity_detail_stage;

import java.math.BigInteger;
import java.time.LocalDateTime;

import br.com.nextgen.DGA_DB_MANAGER.domain.activity_detail.ActivityDetail;
import br.com.nextgen.DGA_DB_MANAGER.domain.activity_stage.ActivityStage;
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
@Entity(name = "activity_detail_stage")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDetailStage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private LocalDateTime start_date;
    private LocalDateTime end_date;

    @OneToOne
    @JoinColumn(name = "id_activity_detail") 
    private ActivityDetail detail;

    @OneToOne
    @JoinColumn(name = "id_activity_stage") 
    private ActivityStage stage;

}

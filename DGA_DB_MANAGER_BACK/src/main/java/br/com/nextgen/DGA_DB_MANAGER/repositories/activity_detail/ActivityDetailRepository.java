package br.com.nextgen.DGA_DB_MANAGER.repositories.activity_detail;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.nextgen.DGA_DB_MANAGER.domain.activity_detail.ActivityDetail;
import br.com.nextgen.DGA_DB_MANAGER.dto.report_activity.ReportActivityResponseDTO;

public interface ActivityDetailRepository extends JpaRepository<ActivityDetail,BigInteger> {
    
    Optional<List<ActivityDetail>> findByActivityId(BigInteger id_activity);

    Optional<List<ActivityDetail>> findByActivityIdAndId(BigInteger id_activity,BigInteger id);
    //caralho isso é complexo de entender 
    @Query("""
        SELECT 
            new br.com.nextgen.DGA_DB_MANAGER.dto.report_activity.ReportActivityResponseDTO(
                ads.detail.id,
                ads.detail.activity.id,
                ads.stage.id,
                ads.detail.title,
                ads.stage.name,
                ads.detail.user.id,
                ads.detail.user.name,
                sum(ads.duration) as duration
            )
        FROM activity_detail_stage ads
        inner join ads.detail 
        inner join ads.stage
        inner join ads.detail.user
        WHERE ads.detail.activity.id  = :activityId
        GROUP BY 
            ads.detail.id,
            ads.detail.activity.id,
            ads.stage.id,
            ads.detail.title,
            ads.stage.name,
            ads.detail.user.id,
            ads.detail.user.name
        
    """)
    List<ReportActivityResponseDTO> findReportActivityByActivityId(@Param("activityId") BigInteger activityId);





}
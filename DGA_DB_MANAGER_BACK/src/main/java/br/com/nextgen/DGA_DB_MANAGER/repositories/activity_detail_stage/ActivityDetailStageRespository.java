package br.com.nextgen.DGA_DB_MANAGER.repositories.activity_detail_stage;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nextgen.DGA_DB_MANAGER.domain.activity_detail_stage.ActivityDetailStage;

public interface ActivityDetailStageRespository extends JpaRepository<ActivityDetailStage,BigInteger> {
    
    Optional<List<ActivityDetailStage>> findByDetailIdAndStageIdAndId(BigInteger id_activity_detail,BigInteger id_activity_stage,BigInteger id);

    Optional<List<ActivityDetailStage>> findByDetailIdAndStageId(BigInteger id_activity_detail,BigInteger id_activity_stage);

    // @Query("SELECT ads FROM activity_detail_stage ads WHERE ads.id_activity = :text")
    // Page<Activity> findByActivityContainingIgnoreCase(@Param("text") String text, Pageable pageable);

 /*   Optional<List<ActivityDetailStage>> findByActivityDetailId(BigInteger id_activity_detail);

    Optional<List<ActivityDetailStage>> findByActivityStageId(BigInteger id_activity_stage); */
}

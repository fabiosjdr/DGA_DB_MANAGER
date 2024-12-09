package br.com.nextgen.DGA_DB_MANAGER.repositories.activity_stage;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nextgen.DGA_DB_MANAGER.domain.activity_stage.ActivityStage;

public interface ActivityStageRepository extends JpaRepository<ActivityStage,BigInteger> {
     Optional<List<ActivityStage>> findByActivityId(BigInteger id_activity);

     Optional<List<ActivityStage>> findByActivityIdAndId(BigInteger id_activity,BigInteger id);

     Optional<List<ActivityStage>> findByActivityIdAndName(BigInteger id_activity,String name);

    // @Query("SELECT a FROM activities a WHERE a.activity  LIKE CONCAT('%', :text, '%') ")
    // Page<Activity> findByActivityContainingIgnoreCase(@Param("text") String text, Pageable pageable);
}
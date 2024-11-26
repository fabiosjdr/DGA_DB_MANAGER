package br.com.nextgen.DGA_DB_MANAGER.repositories.activity_detail;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nextgen.DGA_DB_MANAGER.domain.activity_detail.ActivityDetail;

public interface ActivityDetailRepository extends JpaRepository<ActivityDetail,BigInteger> {
    
     Optional<List<ActivityDetail>> findByActivityId(BigInteger id_activity);

     Optional<List<ActivityDetail>> findByActivityIdAndId(BigInteger id_activity,BigInteger id);

    // @Query("SELECT a FROM activities a WHERE a.activity  LIKE CONCAT('%', :text, '%') ")
    // Page<Activity> findByActivityContainingIgnoreCase(@Param("text") String text, Pageable pageable);
}
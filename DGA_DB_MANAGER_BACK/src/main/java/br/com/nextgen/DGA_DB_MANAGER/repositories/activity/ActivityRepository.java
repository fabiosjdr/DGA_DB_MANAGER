package br.com.nextgen.DGA_DB_MANAGER.repositories.activity;


import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.nextgen.DGA_DB_MANAGER.domain.activity.Activity;

public interface ActivityRepository extends JpaRepository<Activity,BigInteger> {
    Optional<Activity> findByActivity(String activity);

    @Query("SELECT a FROM activities a WHERE a.activity  LIKE CONCAT('%', :text, '%') ")
    Page<Activity> findByActivityContainingIgnoreCase(@Param("text") String text, Pageable pageable);
}

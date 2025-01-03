package br.com.nextgen.DGA_DB_MANAGER.repositories.status;



import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.nextgen.DGA_DB_MANAGER.domain.status.Status;


public interface StatusRepository extends JpaRepository<Status,String> {

    Optional<Status> findByName(String name);

    @Query("SELECT s FROM status s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :text, '%'))")
    Page<Status> findByNameContainingIgnoreCase(@Param("text") String text, Pageable pageable);
    
}


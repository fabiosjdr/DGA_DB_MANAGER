package br.com.nextgen.DGA_DB_MANAGER.repositories.project;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.nextgen.DGA_DB_MANAGER.domain.project.Project;


public interface ProjectRepository extends JpaRepository<Project,String> {
    Optional<Project> findByName(String name);

    @Query("SELECT c FROM projects c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :text, '%')) OR LOWER(c.description) LIKE LOWER(CONCAT('%', :text, '%'))")
    Page<Project> findByNameContainingIgnoreCase(@Param("text") String text, Pageable pageable);
}

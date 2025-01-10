package br.com.nextgen.DGA_DB_MANAGER.repositories.project;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.nextgen.DGA_DB_MANAGER.domain.account.Account;
import br.com.nextgen.DGA_DB_MANAGER.domain.project.Project;


public interface ProjectRepository extends JpaRepository<Project,String> {
    Optional<Project> findByNameAndAccount(String name,Account account);

    List<Project> findByAccount(Account account);

    @Query("SELECT p FROM projects p WHERE p.account.id = :accountId and ( LOWER(p.name) LIKE LOWER(CONCAT('%', :text, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :text, '%')))")
    Page<Project> findByNameContainingIgnoreCase(@Param("text") String text, Pageable pageable,@Param("accountId") BigInteger accountId);
}

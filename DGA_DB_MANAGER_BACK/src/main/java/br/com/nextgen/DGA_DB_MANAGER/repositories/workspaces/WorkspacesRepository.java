package br.com.nextgen.DGA_DB_MANAGER.repositories.workspaces;



import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.nextgen.DGA_DB_MANAGER.domain.account.Account;
import br.com.nextgen.DGA_DB_MANAGER.domain.workspaces.Workspaces;


public interface WorkspacesRepository extends JpaRepository<Workspaces,String> {

    Optional<Workspaces> findByNameAndAccount(String name,Account account);

    List<Workspaces> findByAccount(Account account);

    @Query("SELECT w FROM workspaces w WHERE w.account.id = :accountId and ( LOWER(w.name) LIKE LOWER(CONCAT('%', :text, '%')))")
    Page<Workspaces> findByNameContainingIgnoreCase(@Param("text") String text, Pageable pageable,@Param("accountId") BigInteger accountId);
    
}


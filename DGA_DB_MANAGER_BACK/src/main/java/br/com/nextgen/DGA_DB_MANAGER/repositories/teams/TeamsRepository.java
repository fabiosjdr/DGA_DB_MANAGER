package br.com.nextgen.DGA_DB_MANAGER.repositories.teams;



import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.nextgen.DGA_DB_MANAGER.domain.account.Account;
import br.com.nextgen.DGA_DB_MANAGER.domain.teams.Teams;


public interface TeamsRepository extends JpaRepository<Teams,String> {

    Optional<Teams> findByTitleAndAccount(String title,Account account);

    List<Teams> findByAccount(Account account);

    @Query("SELECT s FROM teams s WHERE s.account.id = :accountId and ( LOWER(s.title) LIKE LOWER(CONCAT('%', :text, '%')))")
    Page<Teams> findByTitleContainingIgnoreCase(@Param("text") String text, Pageable pageable,@Param("accountId") BigInteger accountId);
    
}


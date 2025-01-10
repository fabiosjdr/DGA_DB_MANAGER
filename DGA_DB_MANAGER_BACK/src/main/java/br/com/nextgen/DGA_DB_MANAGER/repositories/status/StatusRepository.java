package br.com.nextgen.DGA_DB_MANAGER.repositories.status;



import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.nextgen.DGA_DB_MANAGER.domain.account.Account;
import br.com.nextgen.DGA_DB_MANAGER.domain.status.Status;


public interface StatusRepository extends JpaRepository<Status,String> {

    Optional<Status> findByNameAndAccount(String name,Account account);

    List<Status> findByAccount(Account account);

    @Query("SELECT s FROM status s WHERE s.account.id = :accountId and ( LOWER(s.name) LIKE LOWER(CONCAT('%', :text, '%')))")
    Page<Status> findByNameContainingIgnoreCase(@Param("text") String text, Pageable pageable,@Param("accountId") BigInteger accountId);
    
}


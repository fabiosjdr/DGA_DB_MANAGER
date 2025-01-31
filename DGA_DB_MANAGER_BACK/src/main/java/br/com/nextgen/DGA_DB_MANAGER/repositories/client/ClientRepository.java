package br.com.nextgen.DGA_DB_MANAGER.repositories.client;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.nextgen.DGA_DB_MANAGER.domain.account.Account;
import br.com.nextgen.DGA_DB_MANAGER.domain.client.Client;


public interface ClientRepository extends JpaRepository<Client,String> {
    Optional<Client> findByNameAndAccount(String name,Account account);

    List<Client> findByAccount(Account account);

    // Método para buscar clientes cujo nome contém a string fornecida
    // @Query("SELECT c FROM client c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    // List<Client> findByNameContainingIgnoreCase(@Param("name") String name);

    @Query("SELECT c FROM client c WHERE c.account.id = :accountId and ( LOWER(c.name) LIKE LOWER(CONCAT('%', :text, '%')) OR LOWER(c.responsable) LIKE LOWER(CONCAT('%', :text, '%')))")
    Page<Client> findByNameContainingIgnoreCase(@Param("text") String text, Pageable pageable,@Param("accountId") BigInteger accountId);
}

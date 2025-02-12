package br.com.nextgen.DGA_DB_MANAGER.repositories.members;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.nextgen.DGA_DB_MANAGER.domain.members.Members;

public interface MembersRepository extends JpaRepository<Members,BigInteger> {

    Optional<List<Members>> findByTeamsId(BigInteger id_team); // findByTeamsId Teams vem do DOMAIN e o Id e a propriedade acessada
    Optional<List<Members>> findByTeamsIdAndId(BigInteger id_team,BigInteger id);
    Optional<List<Members>> findByUserIdAndId(BigInteger id_user,BigInteger id);

    @Query("SELECT tm FROM team_members tm JOIN tm.user u JOIN tm.teams t WHERE t.account.id = :accountId AND t.id = :teamId AND  (LOWER(u.name) LIKE LOWER(CONCAT('%', :text, '%')) )")
    Page<Members> findByNameContainingIgnoreCase(@Param("text") String text, Pageable pageable,@Param("accountId") BigInteger accountId,@Param("teamId") BigInteger id_team);

}

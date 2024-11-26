package br.com.nextgen.DGA_DB_MANAGER.repositories.account_user;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nextgen.DGA_DB_MANAGER.domain.account_user.AccountUser;

public interface AccountUserRepository extends JpaRepository<AccountUser,BigInteger> {
    //nosso amigo JPA sabe  q essa propriedade manytoone e sabe que tem um id na classe user
    Optional<AccountUser> findByUserId(BigInteger userId);

 }

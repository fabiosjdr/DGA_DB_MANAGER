package br.com.nextgen.DGA_DB_MANAGER.repositories.account;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nextgen.DGA_DB_MANAGER.domain.account.Account;

public interface AccountRepository extends JpaRepository<Account,BigInteger> {
   // Optional<Account> findByEmail(String email);
}

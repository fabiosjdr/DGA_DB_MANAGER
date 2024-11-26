package br.com.nextgen.DGA_DB_MANAGER.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.nextgen.DGA_DB_MANAGER.domain.account.Account;
import br.com.nextgen.DGA_DB_MANAGER.domain.account_user.AccountUser;
import br.com.nextgen.DGA_DB_MANAGER.domain.user.User;
import br.com.nextgen.DGA_DB_MANAGER.repositories.account.AccountRepository;
import br.com.nextgen.DGA_DB_MANAGER.repositories.account_user.AccountUserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountUserRepository accountUserRepository;
    private final AccountRepository accountRepository;
    
    public User getAuthUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
    
        User authUser = null;
    
        if (principal instanceof User userDetails) {
            authUser = userDetails; 
        }

        return authUser;
    }


    public Account getAccount(){

        User authUser           = this.getAuthUser();
        AccountUser accountUser = accountUserRepository.findByUserId(authUser.getId()).orElseThrow(() -> new RuntimeException("Account not found"));
        Account account         = accountRepository.findById(accountUser.getAccount().getId()).orElseThrow(() -> new RuntimeException("Account not found"));

        return account;
    }
}

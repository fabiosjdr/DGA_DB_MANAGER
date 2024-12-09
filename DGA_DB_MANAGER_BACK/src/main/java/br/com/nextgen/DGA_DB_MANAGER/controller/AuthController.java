package br.com.nextgen.DGA_DB_MANAGER.controller;


import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nextgen.DGA_DB_MANAGER.domain.account.Account;
import br.com.nextgen.DGA_DB_MANAGER.domain.account_user.AccountUser;
import br.com.nextgen.DGA_DB_MANAGER.domain.user.User;
import br.com.nextgen.DGA_DB_MANAGER.dto.auth.LoginRequestDTO;
import br.com.nextgen.DGA_DB_MANAGER.dto.auth.RegisterRequestDTO;
import br.com.nextgen.DGA_DB_MANAGER.dto.auth.ResponseDTO;
import br.com.nextgen.DGA_DB_MANAGER.infra.security.TokenService;
import br.com.nextgen.DGA_DB_MANAGER.repositories.account.AccountRepository;
import br.com.nextgen.DGA_DB_MANAGER.repositories.account_user.AccountUserRepository;
import br.com.nextgen.DGA_DB_MANAGER.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor //lombok ja cria o construtor para n precisar colocar autowired em cada classe

public class AuthController {

    private final UserRepository  repository;
    private final AccountRepository accountRepository;
    private final AccountUserRepository accountUserRepository;

    private final PasswordEncoder passwordEncoder;
    private final TokenService    tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){

        String teste = passwordEncoder.encode( body.password());
        
        User user = this.repository.findByEmail(body.email()).orElseThrow( ()-> new RuntimeException("User not found") );

        if(passwordEncoder.matches(body.password(),user.getPassword())){
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getName(), token));
        }

        return ResponseEntity.badRequest().build();

    }
    
    @Transactional
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
        
        Optional<User> user = this.repository.findByEmail(body.email());
        LocalDateTime now = LocalDateTime.now();
        
        if(user.isEmpty()){

            Account newaccount = new Account();
                    newaccount.setCreated_at(now);
                    
            this.accountRepository.save(newaccount);

            User newuser = new User();
                 newuser.setEmail(body.email());
                 newuser.setName(body.name());
                 newuser.setPassword(passwordEncoder.encode( body.password() ));

            this.repository.save(newuser);
            String token = this.tokenService.generateToken(newuser);
                
            AccountUser newAccountUser = new AccountUser();
                        newAccountUser.setAccount(newaccount);
                        newAccountUser.setUser(newuser);

            this.accountUserRepository.save(newAccountUser);

            return ResponseEntity.ok(new ResponseDTO(newuser.getName(), token));
        }           
        

        return ResponseEntity.badRequest().build();

    }

}

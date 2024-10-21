package br.com.nextgen.DGA_DB_MANAGER.controller;


import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nextgen.DGA_DB_MANAGER.domain.user.User;
import br.com.nextgen.DGA_DB_MANAGER.dto.LoginRequestDTO;
import br.com.nextgen.DGA_DB_MANAGER.dto.RegisterRequestDTO;
import br.com.nextgen.DGA_DB_MANAGER.dto.ResponseDTO;
import br.com.nextgen.DGA_DB_MANAGER.infra.security.TokenService;
import br.com.nextgen.DGA_DB_MANAGER.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor //lombok ja cria o construtor para n precisar colocar autowired em cada classe

public class AuthController {

    private final UserRepository  repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService    tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        
        User user = this.repository.findByEmail(body.email()).orElseThrow( ()-> new RuntimeException("User not found") );

        if(passwordEncoder.matches(body.password(),user.getPassword())){
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getName(), token));
        }

        return ResponseEntity.badRequest().build();

    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
        
        Optional<User> user = this.repository.findByEmail(body.email());
       
        if(user.isEmpty()){

            User newuser = new User();
                 newuser.setEmail(body.email());
                 newuser.setName(body.name());
                 newuser.setPassword(passwordEncoder.encode( body.password() ));

                 this.repository.save(newuser);

                 String token = this.tokenService.generateToken(newuser);
                 return ResponseEntity.ok(new ResponseDTO(newuser.getName(), token));
        }
        

        return ResponseEntity.badRequest().build();

    }

}

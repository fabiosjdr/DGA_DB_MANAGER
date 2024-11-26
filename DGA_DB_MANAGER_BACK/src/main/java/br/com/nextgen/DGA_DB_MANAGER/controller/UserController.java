package br.com.nextgen.DGA_DB_MANAGER.controller;

import java.math.BigInteger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nextgen.DGA_DB_MANAGER.domain.user.User;
import br.com.nextgen.DGA_DB_MANAGER.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
   
    // @autowired
    private final UserRepository repository;
    
    @GetMapping
    public ResponseEntity  get(){
        var all = this.repository.findAll();
        return ResponseEntity.ok(all);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity  getByID(@PathVariable BigInteger id){

        User user = this.repository.findById(id).orElse(null);
        
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(user);
    }

    
   

}

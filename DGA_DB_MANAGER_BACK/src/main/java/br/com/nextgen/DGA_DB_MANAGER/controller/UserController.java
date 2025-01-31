package br.com.nextgen.DGA_DB_MANAGER.controller;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.nextgen.DGA_DB_MANAGER.domain.account.Account;
import br.com.nextgen.DGA_DB_MANAGER.domain.account_user.AccountUser;
import br.com.nextgen.DGA_DB_MANAGER.domain.user.User;
import br.com.nextgen.DGA_DB_MANAGER.domain.user_roles.UserRoles;
import br.com.nextgen.DGA_DB_MANAGER.dto.user.UserMeResponseDTO;
import br.com.nextgen.DGA_DB_MANAGER.dto.user.UserRequestDTO;
import br.com.nextgen.DGA_DB_MANAGER.dto.user.UserResponseDTO;
import br.com.nextgen.DGA_DB_MANAGER.repositories.account_user.AccountUserRepository;
import br.com.nextgen.DGA_DB_MANAGER.repositories.user.UserRepository;
import br.com.nextgen.DGA_DB_MANAGER.repositories.user_roles.UserRolesRepository;
import br.com.nextgen.DGA_DB_MANAGER.service.AuthService;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
   
    // @autowired
    private final UserRepository        repository;
    private final UserRolesRepository   userRolesRepository;
    private final AccountUserRepository accountUserRepository;
    private final PasswordEncoder       passwordEncoder;
    private final AuthService           authService;

    @GetMapping("/me")
    public ResponseEntity<UserMeResponseDTO>  me(){
        
        User authUser = authService.getAuthUser();
        
        return ResponseEntity.ok(new UserMeResponseDTO(authUser.getId(),authUser.getName(), authUser.getEmail(),authUser.getActive(),authUser.getRoles()));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserResponseDTO>> searchByText(
            @RequestParam(required = false) String text,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
        ) {
            

        Account account = authService.getAccount();

        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = repository.findByNameContainingIgnoreCase(text,pageable,account.getId());

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Page<UserResponseDTO> response = users.map(user -> 
            new UserResponseDTO(user.getId(),user.getName(), user.getEmail(),user.getActive(),user.getRoles(),user.getAccounts())
        );
    
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?>  get(){
        
        Account account = authService.getAccount();
        var findAll = this.repository.findByUserAccount(account.getId());

        return ResponseEntity.ok(findAll);

        //     var all = this.repository.findAll();
            
        //     List<UserResponseDTO> userResponseDTOList = all.stream()
        //     .map(user -> new UserResponseDTO(
        //         user.getId(),
        //         user.getName(),
        //         user.getEmail(),
        //         user.getActive(),
        //         user.getRoles(),
        //         user.getAccounts()
        //     ))
        //     .toList(); // Java 17 suporta o método toList()

        // return ResponseEntity.ok(userResponseDTOList);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO>  getByID(@PathVariable BigInteger id){

        User user = this.repository.findById(id).orElse(null);
        
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok( new UserResponseDTO(user.getId(),user.getName(), user.getEmail(),user.getActive(),user.getRoles(),user.getAccounts()));
    }

    @PutMapping
    public ResponseEntity<UserResponseDTO> update(@RequestBody @Validated UserRequestDTO body){

        User        user  = this.repository.findById(body.id()).orElse(null);
        UserRoles   roles = userRolesRepository.findById(body.id_role().toString()).orElseThrow(() -> new RuntimeException("Id role not found"));
        
        if(user != null){

            user.setName(body.name());
            user.setEmail(body.email());
            user.setRoles(roles);

            if(body.password() != null){
                user.setPassword(passwordEncoder.encode( body.password() ));
            }
            
            this.repository.save(user);

            return ResponseEntity.ok( new UserResponseDTO(user.getId(),user.getName(), user.getEmail(),user.getActive(),user.getRoles(),user.getAccounts()));

        }else{

            return ResponseEntity.notFound().build();
        }
        

    }

    @Transactional
    @PostMapping
    public ResponseEntity create(@RequestBody @Validated UserRequestDTO body){

        Optional<User>  user  = this.repository.findByEmail(body.email());
        UserRoles  roles = userRolesRepository.findById(body.id_role().toString()).orElseThrow(() -> new RuntimeException("Id role not found"));
        
        if(user.isEmpty()){ 
          
            User    newObj = new User();
                    newObj.setName(body.name());
                    newObj.setEmail(body.email());  
                    newObj.setActive(body.active());  
                    newObj.setPassword(passwordEncoder.encode( body.password() ));
                    newObj.setRoles(roles);

                    this.repository.save(newObj);

                    

                    this.registerAccountUser(newObj);

                    return ResponseEntity.ok(newObj);
        }
        

        return ResponseEntity.badRequest().build();

    }

    private AccountUser registerAccountUser(User user){

        Account account = authService.getAccount();

        AccountUser  accountUser = new AccountUser();
        accountUser.setAccount(account);
        accountUser.setUser(user);

        this.accountUserRepository.save(accountUser);

        return accountUser;

    } 

    @DeleteMapping("/{id}")
    public ResponseEntity<?>  delete(@PathVariable BigInteger id){
        
        User domain = this.repository.findById(id).orElse(null);
        
        if (domain == null) {
            return ResponseEntity.notFound().build();
        }
        
        this.repository.delete(domain);

        return ResponseEntity.ok(domain);
    }

    
   

}

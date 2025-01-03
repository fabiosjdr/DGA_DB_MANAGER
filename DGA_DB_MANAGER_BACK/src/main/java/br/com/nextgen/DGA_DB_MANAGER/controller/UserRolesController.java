package br.com.nextgen.DGA_DB_MANAGER.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nextgen.DGA_DB_MANAGER.domain.user_roles.UserRoles;
import br.com.nextgen.DGA_DB_MANAGER.dto.user_roles.UserRolesRequestDTO;
import br.com.nextgen.DGA_DB_MANAGER.repositories.user_roles.UserRolesRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user_roles")
@RequiredArgsConstructor //lombok ja cria o construtor para n precisar colocar autowired em cada classe
public class UserRolesController {

    private final UserRolesRepository repository;

    // @GetMapping("/search")
    // public ResponseEntity<Page<StatusResponseDTO>> searchByText(
    //         @RequestParam(required = false) String text,
    //         @RequestParam(defaultValue = "0") int page,
    //         @RequestParam(defaultValue = "10") int size
    //     ) {
            
    //     Pageable pageable = PageRequest.of(page, size);
    //     Page<Status> status = repository.findByNameContainingIgnoreCase(text,pageable);

    //     if (status.isEmpty()) {
    //         return ResponseEntity.noContent().build();
    //     }

    //     Page<StatusResponseDTO> response = status.map(stat -> 
    //         new StatusResponseDTO(stat.getId(),stat.getName())
    //     );
    
    //     return ResponseEntity.ok(response);
    // }


    @GetMapping
    public ResponseEntity  get(){
        var all = this.repository.findAll();
        return ResponseEntity.ok(all);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity  getByID(@PathVariable String id){

        UserRoles domain = this.repository.findById(id).orElse(null);
        
        if (domain == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(domain);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Validated UserRolesRequestDTO body){

        Optional<UserRoles> domain = this.repository.findByName(body.name());
        
        if(domain.isEmpty()){

            UserRoles newObj = new UserRoles();
                      newObj.setName(body.name());

                      this.repository.save(newObj);

                    return ResponseEntity.ok(newObj);
        }
        

        return ResponseEntity.badRequest().build();

    }

    @PutMapping
    public ResponseEntity update(@RequestBody @Validated UserRolesRequestDTO body){

        UserRoles domain = this.repository.findById(body.id().toString()).orElse(null);
        
        if(domain != null){

            domain.setName(body.name());
            this.repository.save(domain);

            return ResponseEntity.ok(domain);

        }else{

            return ResponseEntity.notFound().build();
        }
        

    }

    @DeleteMapping("/{id}")
    public ResponseEntity  delete(@PathVariable String id){
        
        UserRoles domain = this.repository.findById(id).orElse(null);
        
        if (domain == null) {
            return ResponseEntity.notFound().build();
        }
        
        this.repository.delete(domain);

        return ResponseEntity.ok(domain);
    }
}

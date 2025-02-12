package br.com.nextgen.DGA_DB_MANAGER.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
import br.com.nextgen.DGA_DB_MANAGER.domain.workspaces.Workspaces;
import br.com.nextgen.DGA_DB_MANAGER.dto.workspaces.WorkspacesRequestDTO;
import br.com.nextgen.DGA_DB_MANAGER.dto.workspaces.WorkspacesResponseDTO;
import br.com.nextgen.DGA_DB_MANAGER.repositories.workspaces.WorkspacesRepository;
import br.com.nextgen.DGA_DB_MANAGER.service.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/workspaces")
@RequiredArgsConstructor //lombok ja cria o construtor para n precisar colocar autowired em cada classe
public class WorkspacesController {

    private final WorkspacesRepository repository;
    private final AuthService        authService;

    @GetMapping("/search")
    public ResponseEntity<Page<WorkspacesResponseDTO>> searchByText(
            @RequestParam(required = false) String text,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
        ) {
            
        Account account = authService.getAccount();
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Workspaces> workspaces = repository.findByNameContainingIgnoreCase(text,pageable,account.getId());

        if (workspaces.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Page<WorkspacesResponseDTO> response = workspaces.map(cat -> 
            new WorkspacesResponseDTO(cat.getId(),cat.getName(),cat.getAccount())
        );
    
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Workspaces>>  get(){

        Account account = authService.getAccount();

        var all = this.repository.findByAccount(account);
        return ResponseEntity.ok(all);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Workspaces>  getByID(@PathVariable String id){

        Workspaces domain = this.repository.findById(id).orElse(null);
        
        if (domain == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(domain);
    }

    @PostMapping
    public ResponseEntity<Workspaces> create(@RequestBody @Validated WorkspacesRequestDTO body){

        Account account = authService.getAccount();

        Optional<Workspaces> domain = this.repository.findByNameAndAccount(body.name(),account);
        
        if(domain.isEmpty()){

            Workspaces  newObj = new Workspaces();
                      newObj.setName(body.name());
                      newObj.setAccount(account);
                      this.repository.save(newObj);

                    return ResponseEntity.ok(newObj);
        }
        

        return ResponseEntity.badRequest().build();

    }

    @PutMapping
    public ResponseEntity<Workspaces> update(@RequestBody @Validated WorkspacesRequestDTO body){

        Account account = authService.getAccount();

        Workspaces domain = this.repository.findById(body.id().toString()).orElse(null);
        
        if(domain != null && account.getId() == domain.getAccount().getId()){

            domain.setName(body.name());
            this.repository.save(domain);

            return ResponseEntity.ok(domain);

        }else{

            return ResponseEntity.notFound().build();
        }
        

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Workspaces>  delete(@PathVariable String id){
        
        Account account = authService.getAccount();

        Workspaces domain = this.repository.findById(id).orElse(null);
        
        if(domain == null || account.getId() != domain.getAccount().getId()){

            return ResponseEntity.notFound().build();
        }
        
        this.repository.delete(domain);

        return ResponseEntity.ok(domain);
    }
}

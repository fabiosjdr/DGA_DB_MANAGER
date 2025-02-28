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
import br.com.nextgen.DGA_DB_MANAGER.domain.teams.Teams;
import br.com.nextgen.DGA_DB_MANAGER.domain.workspaces.Workspaces;
import br.com.nextgen.DGA_DB_MANAGER.dto.teams.TeamsRequestDTO;
import br.com.nextgen.DGA_DB_MANAGER.dto.teams.TeamsResponseDTO;
import br.com.nextgen.DGA_DB_MANAGER.repositories.teams.TeamsRepository;
import br.com.nextgen.DGA_DB_MANAGER.repositories.workspaces.WorkspacesRepository;
import br.com.nextgen.DGA_DB_MANAGER.service.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor //lombok ja cria o construtor para n precisar colocar autowired em cada classe
public class TeamsController {

    private final TeamsRepository repository;
    private final WorkspacesRepository workspacesRepository;
    private final AuthService     authService;

    @GetMapping("/search")
    public ResponseEntity<Page<TeamsResponseDTO>> searchByText(
            @RequestParam(required = false) String text,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
        ) {
            
        Account account = authService.getAccount();
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Teams> teams = repository.findByTitleContainingIgnoreCase(text,pageable,account.getId());

        if (teams.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Page<TeamsResponseDTO> response = teams.map(team -> 
            new TeamsResponseDTO(team.getId(),team.getTitle(),team.getDescription(),team.getAccount(),team.getWorkspaces())
        );
    
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Teams>>  get(){

        Account account = authService.getAccount();

        var all = this.repository.findByAccount(account);
        return ResponseEntity.ok(all);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Teams>  getByID(@PathVariable String id){

        Teams domain = this.repository.findById(id).orElse(null);
        
        if (domain == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(domain);
    }

    @PostMapping
    public ResponseEntity<Teams> create(@RequestBody @Validated TeamsRequestDTO body){

        Account account = authService.getAccount();

        Workspaces workspaces  = workspacesRepository.findById(body.id_workspaces().toString()).orElseThrow(() -> new RuntimeException("Client not found"));

        Optional<Teams> domain = this.repository.findByTitleAndAccount(body.title(),account);
        
        if(domain.isEmpty()){

            Teams  newObj = new Teams();
                      newObj.setTitle(body.title());
                      newObj.setDescription(body.description());
                      newObj.setWorkspaces(workspaces);
                      newObj.setAccount(account);
                      this.repository.save(newObj);

                    return ResponseEntity.ok(newObj);
        }
        

        return ResponseEntity.badRequest().build();

    }

    @PutMapping
    public ResponseEntity<Teams> update(@RequestBody @Validated TeamsRequestDTO body){

        Account account = authService.getAccount();

        Teams domain = this.repository.findById(body.id().toString()).orElse(null);
        Workspaces workspaces = this.workspacesRepository.findById(body.id_workspaces().toString()).orElse(null);
        
        if(domain != null && account.getId() == domain.getAccount().getId()){

            domain.setTitle(body.title());
            domain.setDescription(body.description());
            domain.setWorkspaces(workspaces);
            this.repository.save(domain);

            return ResponseEntity.ok(domain);

        }else{

            return ResponseEntity.notFound().build();
        }
        

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Teams>  delete(@PathVariable String id){
        
        Account account = authService.getAccount();

        Teams domain = this.repository.findById(id).orElse(null);
        
        if(domain == null || account.getId() != domain.getAccount().getId()){

            return ResponseEntity.notFound().build();
        }
        
        this.repository.delete(domain);

        return ResponseEntity.ok(domain);
    }
}

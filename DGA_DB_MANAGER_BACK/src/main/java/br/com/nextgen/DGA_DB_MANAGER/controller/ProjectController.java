package br.com.nextgen.DGA_DB_MANAGER.controller;

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
import br.com.nextgen.DGA_DB_MANAGER.domain.client.Client;
import br.com.nextgen.DGA_DB_MANAGER.domain.project.Project;
import br.com.nextgen.DGA_DB_MANAGER.dto.project.ProjectResponseDTO;
import br.com.nextgen.DGA_DB_MANAGER.dto.project.ProjectsRequestDTO;
import br.com.nextgen.DGA_DB_MANAGER.repositories.client.ClientRepository;
import br.com.nextgen.DGA_DB_MANAGER.repositories.project.ProjectRepository;
import br.com.nextgen.DGA_DB_MANAGER.service.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor //lombok ja cria o construtor para n precisar colocar autowired em cada classe

public class ProjectController {

    private final ProjectRepository repository;
    private final ClientRepository  clientRepository;
    private final AuthService       authService;

    @GetMapping("/search")
    public ResponseEntity<Page<ProjectResponseDTO>> searchByText(
            @RequestParam(required = false) String text,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
        ) {

        Account account = authService.getAccount();
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Project> projects = repository.findByNameContainingIgnoreCase(text,pageable,account.getId());

        if (projects.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Page<ProjectResponseDTO> response = projects.map(project -> 
            new ProjectResponseDTO(project.getId(),project.getName(),project.getClient(),project.getDescription(),project.getStart_date(),project.getEnd_date(),project.getAccount())
        );
    
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?>  get(){
        var findAll = this.repository.findAll();
        return ResponseEntity.ok(findAll);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project>  getByID(@PathVariable String id){

        Account account = authService.getAccount();

        Project project = this.repository.findById(id).orElse(null);
        
        if (project == null || account.getId() != project.getAccount().getId()) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(project);
    }

    @PostMapping
    public ResponseEntity<Project> create(@RequestBody @Validated ProjectsRequestDTO body){

        Account account = authService.getAccount();

        Optional<Project> project = this.repository.findByNameAndAccount(body.name(),account);
        
        Client client = clientRepository.findById(body.id_client().toString()).orElseThrow(() -> new RuntimeException("Client not found"));

        if(project.isEmpty()){

            Project  newObj = new Project();
                     newObj.setName(body.name());
                     newObj.setDescription(body.description());
                     newObj.setClient(client);
                     newObj.setStart_date(body.start_date());
                     newObj.setEnd_date(body.end_date());
                     newObj.setAccount(account);
                     this.repository.save(newObj);

                    return ResponseEntity.ok(newObj);
        }
        

        return ResponseEntity.badRequest().build();

    }

    @PutMapping
    public ResponseEntity update(@RequestBody @Validated ProjectsRequestDTO body){

      
        Project project = this.repository.findById(body.id().toString()).orElse(null);
        Client client   = clientRepository.findById(body.id_client().toString()).orElseThrow(() -> new RuntimeException("Client not found"));
        
        Account account = authService.getAccount();

        if(project != null && account.getId() == project.getAccount().getId()){

            project.setName(body.name());
            project.setDescription(body.description());
            project.setClient(client);
            project.setStart_date(body.start_date());
            project.setEnd_date(body.end_date());
            project.setAccount(account);
            
            this.repository.save(project);

            return ResponseEntity.ok(project);
            
        }else{

            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity  delete(@PathVariable String id){
        
        Account account = authService.getAccount();

        Project project = this.repository.findById(id).orElse(null);
        
        if (project == null || account.getId() != project.getAccount().getId()) {
            return ResponseEntity.notFound().build();
        }
        
        this.repository.delete(project);

        return ResponseEntity.ok(project);
    }
}

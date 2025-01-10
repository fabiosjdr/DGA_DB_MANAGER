package br.com.nextgen.DGA_DB_MANAGER.controller;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import br.com.nextgen.DGA_DB_MANAGER.domain.activity.Activity;
import br.com.nextgen.DGA_DB_MANAGER.domain.activity_stage.ActivityStage;
import br.com.nextgen.DGA_DB_MANAGER.domain.category.Category;
import br.com.nextgen.DGA_DB_MANAGER.domain.client.Client;
import br.com.nextgen.DGA_DB_MANAGER.domain.project.Project;
import br.com.nextgen.DGA_DB_MANAGER.domain.status.Status;
import br.com.nextgen.DGA_DB_MANAGER.domain.user.User;
import br.com.nextgen.DGA_DB_MANAGER.dto.activity.ActivityRequestDTO;
import br.com.nextgen.DGA_DB_MANAGER.dto.activity.ActivityResponseDTO;
import br.com.nextgen.DGA_DB_MANAGER.repositories.activity.ActivityRepository;
import br.com.nextgen.DGA_DB_MANAGER.repositories.activity_stage.ActivityStageRepository;
import br.com.nextgen.DGA_DB_MANAGER.repositories.category.CategoryRepository;
import br.com.nextgen.DGA_DB_MANAGER.repositories.client.ClientRepository;
import br.com.nextgen.DGA_DB_MANAGER.repositories.project.ProjectRepository;
import br.com.nextgen.DGA_DB_MANAGER.repositories.status.StatusRepository;
import br.com.nextgen.DGA_DB_MANAGER.service.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/activity")
@RequiredArgsConstructor //lombok ja cria o construtor para n precisar colocar autowired em cada classe
public class ActivityController{

    private final ActivityRepository  repository;
    private final ClientRepository    clientRepository;
    private final CategoryRepository  categoryRepository;
    private final ProjectRepository   projectRepository;
    private final StatusRepository    statusRepository;
    private final ActivityStageRepository stageRepository;
    
    private final AuthService         authService;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/search")
    public ResponseEntity<Page<ActivityResponseDTO>> searchByText(
            @RequestParam(required = false) String text,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
        ) {


        Account account = authService.getAccount();
            
        Pageable pageable = PageRequest.of(page, size);
        Page<Activity> activities = repository.findByActivityContainingIgnoreCase(text,pageable,account.getId());

        if (activities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Page<ActivityResponseDTO> response = activities.map(activitie -> 
            new ActivityResponseDTO(
                activitie.getId(),
                activitie.getActivity(),
                activitie.getClient(),
                activitie.getCategory(),
                activitie.getProject(),
               // activitie.getStatus(),
                activitie.getStart_date(),
                activitie.getEnd_date(),
                activitie.getUser(),
                activitie.getAccount()
            )
        );
    
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Activity>>  get(){
        var all = this.repository.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Activity>  getByID(@PathVariable BigInteger id){

        Activity domain = this.repository.findById(id).orElse(null);
        
        if (domain == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(domain);
    }
    
    @Transactional
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Validated ActivityRequestDTO body){


        User authUser = authService.getAuthUser();

        if (authUser.getId() == null) {
            // Trate o caso em que o userId não pôde ser obtido
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado ou ID não disponível");
        }

        Account account = authService.getAccount();

        Client   client   = clientRepository.findById(body.id_client().toString()).orElseThrow(() -> new RuntimeException("Client not found"));
        Category category = categoryRepository.findById(body.id_category().toString()).orElseThrow(() -> new RuntimeException("Category not found"));
        Project  project  = projectRepository.findById(body.id_project().toString()).orElseThrow(() -> new RuntimeException("Project not found"));
        //Status   status   = statusRepository.findById(body.id_status().toString()).orElseThrow(() -> new RuntimeException("Status not found"));
        
        LocalDateTime startDate   = (body.end_date() != null)   ?  LocalDateTime.parse(body.start_date(), formatter)   : null;
        LocalDateTime endDate     = (body.end_date() != null)   ?  LocalDateTime.parse(body.end_date(), formatter)   : null;

        Activity  newObj = new Activity();
                  newObj.setActivity(body.activity());
                  newObj.setStart_date(startDate);
                  newObj.setEnd_date(endDate);
                  newObj.setCategory(category);
                  newObj.setClient(client);
                  newObj.setProject(project);
                  //newObj.setStatus(status);
                  newObj.setUser(authUser);
                  newObj.setAccount(account);
        
        this.repository.save(newObj);


        this.createDetail(newObj);

        return ResponseEntity.ok(newObj);
     
    }

    private void createDetail(Activity activity){
        
        List<Status> statuses = statusRepository.findAll();

        for (Status status : statuses) {

            ActivityStage  activeStage = new ActivityStage();
            
            activeStage.setActivity(activity);
            activeStage.setName(status.getName());
            activeStage.setTimer(status.getTimer());

            this.stageRepository.save(activeStage);
            
        }

    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Validated ActivityRequestDTO body){

        User authUser = authService.getAuthUser();

        if (authUser.getId() == null) {
            // Trate o caso em que o userId não pôde ser obtido
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado ou ID não disponível");
        }

        Client   client   = clientRepository.findById(body.id_client().toString()).orElseThrow(() -> new RuntimeException("Client not found"));
        Category category = categoryRepository.findById(body.id_category().toString()).orElseThrow(() -> new RuntimeException("Category not found"));
        Project  project  = projectRepository.findById(body.id_project().toString()).orElseThrow(() -> new RuntimeException("Project not found"));
        //Status   status   = statusRepository.findById(body.id_project().toString()).orElseThrow(() -> new RuntimeException("Status not found"));

        Activity domain = this.repository.findById(body.id()).orElse(null);

        LocalDateTime startDate = (body.end_date() != null)   ?  LocalDateTime.parse(body.start_date(), formatter) : null;
        LocalDateTime endDate   = (body.end_date() != null)   ?  LocalDateTime.parse(body.end_date(), formatter)   : null;

        
        if(domain != null){

            domain.setActivity(body.activity());
            domain.setStart_date(startDate);
            domain.setEnd_date(endDate);
            domain.setCategory(category);
            domain.setClient(client);
            domain.setProject(project);
          //  domain.setStatus(status);
            domain.setUser(authUser);

            this.repository.save(domain);

            return ResponseEntity.ok(domain);

        }else{

            return ResponseEntity.notFound().build();
        }
        

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Activity>  delete(@PathVariable BigInteger id){
        
        Activity domain = this.repository.findById(id).orElse(null);
        
        if (domain == null) {
            return ResponseEntity.notFound().build();
        }
        
        this.repository.delete(domain);

        return ResponseEntity.ok(domain);
    }
}

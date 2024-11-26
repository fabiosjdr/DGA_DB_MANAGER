package br.com.nextgen.DGA_DB_MANAGER.controller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.http.HttpStatus;
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

import br.com.nextgen.DGA_DB_MANAGER.domain.activity.Activity;
import br.com.nextgen.DGA_DB_MANAGER.domain.activity_detail.ActivityDetail;
import br.com.nextgen.DGA_DB_MANAGER.domain.activity_stage.ActivityStage;
import br.com.nextgen.DGA_DB_MANAGER.domain.user.User;
import br.com.nextgen.DGA_DB_MANAGER.dto.activity_detail.ActivityDetailRequestDTO;
import br.com.nextgen.DGA_DB_MANAGER.repositories.activity.ActivityRepository;
import br.com.nextgen.DGA_DB_MANAGER.repositories.activity_detail.ActivityDetailRepository;
import br.com.nextgen.DGA_DB_MANAGER.repositories.activity_stage.ActivityStageRepository;
import br.com.nextgen.DGA_DB_MANAGER.service.AuthService;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/activity_detail")
@RequiredArgsConstructor //lombok ja cria o construtor para n precisar colocar autowired em cada classe
public class ActivityDetailController {

    private final ActivityDetailRepository repository;
    private final ActivityRepository       activityRepository;
    private final ActivityStageRepository  activityStageRepository;
    private final AuthService              authService;

    @GetMapping("/{id_activity}")
    public ResponseEntity<List<ActivityDetail>>  get(@PathVariable BigInteger id_activity){
        List<ActivityDetail> domain = this.repository.findByActivityId(id_activity).orElse(null);
        return ResponseEntity.ok(domain);
    }

    @GetMapping()
    public ResponseEntity<List<ActivityDetail>>  getByID(@RequestBody @Validated ActivityDetailRequestDTO body){

        List<ActivityDetail> domain ;

        if (body.id() == null) {
            domain = this.repository.findByActivityId(body.id_activity()).orElse(null);
        }else{
            domain = this.repository.findByActivityIdAndId(body.id_activity(),body.id()).orElse(null);
        }
        
        if (domain == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(domain);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Validated ActivityDetailRequestDTO body){

        User authUser = authService.getAuthUser();

        if (authUser.getId() == null) {
            // Trate o caso em que o userId não pôde ser obtido
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado ou ID não disponível");
        }

        //Account account = authService.getAccount();

        Activity      activity   = activityRepository.findById(body.id_activity()).orElseThrow(() -> new RuntimeException("activity not found"));
        ActivityStage stage      = activityStageRepository.findById(body.id_stage()).orElseThrow(() -> new RuntimeException("stage not found"));

        ActivityDetail  newObj = new ActivityDetail();
                        newObj.setActivity(activity);
                        newObj.setTitle(body.title());
                        newObj.setTitle(body.description());
                        newObj.setUser(authUser);
                        newObj.setStage(stage);
        
        this.repository.save(newObj);
        return ResponseEntity.ok(newObj);
     
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Validated ActivityDetailRequestDTO body){

        User authUser = authService.getAuthUser();

        if (authUser.getId() == null) {
            // Trate o caso em que o userId não pôde ser obtido
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado ou ID não disponível");
        }
        
        ActivityDetail  domain   = this.repository.findById(body.id()).orElse(null);
        Activity        activity = activityRepository.findById(body.id_activity()).orElseThrow(() -> new RuntimeException("activity not found"));
        ActivityStage   stage    = activityStageRepository.findById(body.id_stage()).orElseThrow(() -> new RuntimeException("activity not found"));
        
        if(domain != null){

            domain.setActivity(activity);
            domain.setTitle(body.title());
            domain.setTitle(body.description());
            domain.setUser(authUser);
            domain.setStage(stage);

            this.repository.save(domain);

            return ResponseEntity.ok(domain);

        }else{

            return ResponseEntity.notFound().build();
        }
        

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ActivityDetail>  delete(@PathVariable BigInteger id){
        
        ActivityDetail domain = this.repository.findById(id).orElse(null);
        
        if (domain == null) {
            return ResponseEntity.notFound().build();
        }
        
        this.repository.delete(domain);

        return ResponseEntity.ok(domain);
    }

}

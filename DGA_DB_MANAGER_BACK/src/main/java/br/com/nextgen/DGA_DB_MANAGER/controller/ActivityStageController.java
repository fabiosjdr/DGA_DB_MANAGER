package br.com.nextgen.DGA_DB_MANAGER.controller;

import java.math.BigInteger;
import java.util.List;

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
import br.com.nextgen.DGA_DB_MANAGER.domain.activity_stage.ActivityStage;
import br.com.nextgen.DGA_DB_MANAGER.dto.activity_stage.ActivityStageRequestDTO;
import br.com.nextgen.DGA_DB_MANAGER.repositories.activity.ActivityRepository;
import br.com.nextgen.DGA_DB_MANAGER.repositories.activity_stage.ActivityStageRepository;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/activity_stage")
@RequiredArgsConstructor
public class ActivityStageController {

    private final ActivityStageRepository repository;
    private final ActivityRepository      activityRepository;

    @GetMapping("/{id_activity}")
    public ResponseEntity<List<ActivityStage>>  get(@PathVariable BigInteger id_activity){
        List<ActivityStage> domain = this.repository.findByActivityId(id_activity).orElse(null);
        return ResponseEntity.ok(domain);
    }

    @GetMapping()
    public ResponseEntity<List<ActivityStage>>  getByID(@RequestBody @Validated ActivityStageRequestDTO body){

        List<ActivityStage> domain ;

        if(body.name() != null){
            domain = this.repository.findByActivityIdAndName(body.id_activity(),body.name()).orElse(null);
        }else if (body.id() == null) {
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
    public ResponseEntity<?> create(@RequestBody @Validated ActivityStageRequestDTO body){

        Activity      activity   = activityRepository.findById(body.id_activity()).orElseThrow(() -> new RuntimeException("activity not found"));
       
        ActivityStage newObj = new ActivityStage();
                      newObj.setActivity(activity);
                      newObj.setName(body.name());
                      newObj.setTimer(body.timer());
                        
        
        this.repository.save(newObj);
        return ResponseEntity.ok(newObj);
     
    }


    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Validated ActivityStageRequestDTO body){

        ActivityStage  domain = this.repository.findById(body.id()).orElse(null);
        Activity     activity = activityRepository.findById(body.id_activity()).orElseThrow(() -> new RuntimeException("activity not found"));
        
        if(domain != null){

            domain.setActivity(activity);

            if(body.name() != null){
              domain.setName(body.name());
            }

            if(body.timer() != null){
                domain.setTimer(body.timer());
            }

            this.repository.save(domain);

            return ResponseEntity.ok(domain);

        }else{

            return ResponseEntity.notFound().build();
        }
        

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ActivityStage>  delete(@PathVariable BigInteger id){
        
        ActivityStage domain = this.repository.findById(id).orElse(null);
        
        if (domain == null) {
            return ResponseEntity.notFound().build();
        }
        
        this.repository.delete(domain);

        return ResponseEntity.ok(domain);
    }
}

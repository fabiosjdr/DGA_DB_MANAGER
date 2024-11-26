package br.com.nextgen.DGA_DB_MANAGER.controller;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import br.com.nextgen.DGA_DB_MANAGER.domain.activity_detail.ActivityDetail;
import br.com.nextgen.DGA_DB_MANAGER.domain.activity_detail_stage.ActivityDetailStage;
import br.com.nextgen.DGA_DB_MANAGER.domain.activity_stage.ActivityStage;
import br.com.nextgen.DGA_DB_MANAGER.dto.activity_detail_stage.ActivityDetailStageRequestDTO;
import br.com.nextgen.DGA_DB_MANAGER.repositories.activity_detail.ActivityDetailRepository;
import br.com.nextgen.DGA_DB_MANAGER.repositories.activity_detail_stage.ActivityDetailStageRespository;
import br.com.nextgen.DGA_DB_MANAGER.repositories.activity_stage.ActivityStageRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/activity_detail_stage")
@RequiredArgsConstructor
public class ActivityDetailStageController {


    private final ActivityStageRepository  stageRepository;
    private final ActivityDetailRepository detailRepository;
    private final ActivityDetailStageRespository repository;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping()
    public ResponseEntity<List<ActivityDetailStage>>  getByID(@RequestBody @Validated ActivityDetailStageRequestDTO body){

        List<ActivityDetailStage> domain ;

        if (body.id() == null) {
            domain = this.repository.findByDetailIdAndStageId(body.id_activity_detail(), body.id_activity_stage()).orElse(null);
        }else{
            domain = this.repository.findByDetailIdAndStageIdAndId(body.id_activity_detail(), body.id_activity_stage(),body.id()).orElse(null);
        }
        
        if (domain == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(domain);
    }

    
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Validated ActivityDetailStageRequestDTO body){

            
        ActivityDetail  detail   = detailRepository.findById(body.id_activity_detail()).orElseThrow(() -> new RuntimeException("activity not found"));
        ActivityStage   stage    = stageRepository.findById(body.id_activity_stage()).orElseThrow(() -> new RuntimeException("stage not found"));
        
        
        // Converter a string para LocalDateTime
        LocalDateTime startDate = (body.start_date() != null) ?  LocalDateTime.parse(body.start_date(), formatter) : LocalDateTime.now();
        LocalDateTime endDate   = (body.end_date() != null)   ?  LocalDateTime.parse(body.end_date(), formatter)   : null;

        ActivityDetailStage newObj = new ActivityDetailStage();
                            newObj.setDetail(detail);
                            newObj.setStage(stage);
                            newObj.setStart_date(startDate);
                            newObj.setEnd_date(endDate);
        
        this.repository.save(newObj);
        return ResponseEntity.ok(newObj);
     
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Validated ActivityDetailStageRequestDTO body){

        ActivityDetail      detail = detailRepository.findById(body.id_activity_detail()).orElseThrow(() -> new RuntimeException("detail not found"));
        ActivityStage       stage  = stageRepository.findById(body.id_activity_stage()).orElseThrow(() -> new RuntimeException("stage not found"));
        ActivityDetailStage domain = repository.findById(body.id()).orElseThrow(() -> new RuntimeException("detail_stage not found"));
        
        LocalDateTime startDate = (body.start_date() != null) ?  LocalDateTime.parse(body.start_date(), formatter) : LocalDateTime.now();
        LocalDateTime endDate   = (body.end_date() != null)   ?  LocalDateTime.parse(body.end_date(), formatter)   : null;

        if(domain != null){

            domain.setDetail(detail);
            domain.setStage(stage);
            domain.setStart_date(startDate);
            domain.setEnd_date(endDate);

            this.repository.save(domain);

            return ResponseEntity.ok(domain);

        }else{

            return ResponseEntity.notFound().build();
        }
        

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ActivityDetailStage>  delete(@PathVariable BigInteger id){
        
        ActivityDetailStage domain = this.repository.findById(id).orElse(null);
        
        if (domain == null) {
            return ResponseEntity.notFound().build();
        }
        
        this.repository.delete(domain);

        return ResponseEntity.ok(domain);
    }

}

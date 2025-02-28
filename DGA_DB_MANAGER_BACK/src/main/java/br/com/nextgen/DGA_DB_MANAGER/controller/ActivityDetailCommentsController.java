package br.com.nextgen.DGA_DB_MANAGER.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nextgen.DGA_DB_MANAGER.domain.activity_detail_comments.ActivityDetailComments;
import br.com.nextgen.DGA_DB_MANAGER.dto.activity_detail_comments.ActivityDetailCommentRequestDTO;
import br.com.nextgen.DGA_DB_MANAGER.repositories.activity_detail_comments.ActivityDetailCommentsRepository;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/activity_detail_comments")
@RequiredArgsConstructor
public class ActivityDetailCommentsController {


     private final ActivityDetailCommentsRepository repository;


    @GetMapping()
    public ResponseEntity<List<ActivityDetailComments>>  getByID(@RequestBody @Validated ActivityDetailCommentRequestDTO body){

        List<ActivityDetailComments> domain ;

        if (body.id() == null) {
            domain = this.repository.findByIdActivityDetail(body.id_activity_detail()).orElse(null);
        }else{
            domain = this.repository.findByIdActivityDetailAndId(body.id_activity_detail(),body.id()).orElse(null);
        }
        
        if (domain == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(domain);
    }
}

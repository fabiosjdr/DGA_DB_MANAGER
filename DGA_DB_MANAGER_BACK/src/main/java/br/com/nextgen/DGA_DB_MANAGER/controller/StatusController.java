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

import br.com.nextgen.DGA_DB_MANAGER.domain.status.Status;
import br.com.nextgen.DGA_DB_MANAGER.dto.status.StatusRequestDTO;
import br.com.nextgen.DGA_DB_MANAGER.dto.status.StatusResponseDTO;
import br.com.nextgen.DGA_DB_MANAGER.repositories.status.StatusRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/status")
@RequiredArgsConstructor //lombok ja cria o construtor para n precisar colocar autowired em cada classe
public class StatusController {

    private final StatusRepository repository;
    @GetMapping("/search")
    public ResponseEntity<Page<StatusResponseDTO>> searchByText(
            @RequestParam(required = false) String text,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
        ) {
            
        Pageable pageable = PageRequest.of(page, size);
        Page<Status> status = repository.findByNameContainingIgnoreCase(text,pageable);

        if (status.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Page<StatusResponseDTO> response = status.map(stat -> 
            new StatusResponseDTO(stat.getId(),stat.getName(),stat.getTimer())
        );
    
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity  get(){
        var all = this.repository.findAll();
        return ResponseEntity.ok(all);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity  getByID(@PathVariable String id){

        Status domain = this.repository.findById(id).orElse(null);
        
        if (domain == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(domain);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Validated StatusRequestDTO body){

        Optional<Status> domain = this.repository.findByName(body.name());
        
        if(domain.isEmpty()){

            Status  newObj = new Status();
                      newObj.setName(body.name());
                      newObj.setTimer(body.timer());  
                      this.repository.save(newObj);

                    return ResponseEntity.ok(newObj);
        }
        

        return ResponseEntity.badRequest().build();

    }

    @PutMapping
    public ResponseEntity update(@RequestBody @Validated StatusRequestDTO body){

        Status domain = this.repository.findById(body.id()).orElse(null);
        
        if(domain != null){

            domain.setName(body.name());
            domain.setTimer(body.timer());
            this.repository.save(domain);

            return ResponseEntity.ok(domain);

        }else{

            return ResponseEntity.notFound().build();
        }
        

    }

    @DeleteMapping("/{id}")
    public ResponseEntity  delete(@PathVariable String id){
        
        Status domain = this.repository.findById(id).orElse(null);
        
        if (domain == null) {
            return ResponseEntity.notFound().build();
        }
        
        this.repository.delete(domain);

        return ResponseEntity.ok(domain);
    }
}

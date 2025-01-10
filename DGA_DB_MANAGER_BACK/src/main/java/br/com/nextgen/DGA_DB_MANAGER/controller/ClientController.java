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
import br.com.nextgen.DGA_DB_MANAGER.dto.client.ClientRequestDTO;
import br.com.nextgen.DGA_DB_MANAGER.dto.client.ClientResponseDTO;
import br.com.nextgen.DGA_DB_MANAGER.repositories.client.ClientRepository;
import br.com.nextgen.DGA_DB_MANAGER.service.AuthService;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/client")
@RequiredArgsConstructor //lombok ja cria o construtor para n precisar colocar autowired em cada classe

public class ClientController {
    
    private final ClientRepository repository;
    private final AuthService      authService;

    // @autowired //RequiredArgsConstructor ja faz essa declaração
    // private  ClientRepository repository;
    
    @GetMapping("/search")

    public ResponseEntity<Page<ClientResponseDTO>> searchByText(
            @RequestParam(required = false) String text,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
        ) {


        Account account = authService.getAccount();
            
        Pageable pageable = PageRequest.of(page, size);
        Page<Client> clients = repository.findByNameContainingIgnoreCase(text,pageable,account.getId());

        if (clients.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Page<ClientResponseDTO> response = clients.map(client -> 
            new ClientResponseDTO(client.getId(),client.getName(), client.getResponsable(),client.getTelephone(),client.getHours(),client.getAccount())
        );
    
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?>  get(){
        var all = this.repository.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client>  getByID(@PathVariable String id){

        Client client = this.repository.findById(id).orElse(null);
        
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(client);
    }

    @PostMapping
    public ResponseEntity<ClientResponseDTO> create(@RequestBody @Validated ClientRequestDTO body){

        Account account = authService.getAccount();

        Optional<Client> client = this.repository.findByNameAndAccount(body.name(),account);
        
        if(client.isEmpty()){

            Client  newclient = new Client();
                    newclient.setName(body.name());
                    newclient.setResponsable(body.responsable());
                    newclient.setTelephone(body.telephone());
                    newclient.setHours(body.hours());
                    newclient.setAccount(account);

                    this.repository.save(newclient);

                    return ResponseEntity.ok(new ClientResponseDTO(newclient.getId(),newclient.getName(), newclient.getResponsable(),newclient.getTelephone(),newclient.getHours(),newclient.getAccount()));
        }
        

        return ResponseEntity.badRequest().build();

    }

    @PutMapping
    public ResponseEntity<ClientResponseDTO> update(@RequestBody @Validated ClientRequestDTO body){

        Client client = this.repository.findById(body.id().toString()).orElse(null);
        
        Account account = authService.getAccount();
        
        if(client != null && account.getId() == client.getAccount().getId()){

            client.setName(body.name());
            client.setResponsable(body.responsable());
            client.setTelephone(body.telephone());
            client.setHours(body.hours());
            client.setAccount(account);
            this.repository.save(client);

            return ResponseEntity.ok(new ClientResponseDTO(client.getId(),client.getName(), client.getResponsable(),client.getTelephone(),client.getHours(),client.getAccount()));

        }else{

            return ResponseEntity.notFound().build();
        }
        

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Client>  delete(@PathVariable String id){

        Account account = authService.getAccount();

        Client client = this.repository.findById(id).orElse(null);
        
        if (client == null || account.getId() == client.getAccount().getId() ) {
            return ResponseEntity.notFound().build();
        }
        
        this.repository.delete(client);

        return ResponseEntity.ok(client);
    }

}

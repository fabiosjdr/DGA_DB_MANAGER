package br.com.nextgen.DGA_DB_MANAGER.controller;

import java.math.BigInteger;
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
import br.com.nextgen.DGA_DB_MANAGER.domain.members.Members;
import br.com.nextgen.DGA_DB_MANAGER.domain.teams.Teams;
import br.com.nextgen.DGA_DB_MANAGER.domain.user.User;
import br.com.nextgen.DGA_DB_MANAGER.dto.members.MembersRequestDTO;
import br.com.nextgen.DGA_DB_MANAGER.dto.members.MembersResponseDTO;
import br.com.nextgen.DGA_DB_MANAGER.repositories.members.MembersRepository;
import br.com.nextgen.DGA_DB_MANAGER.repositories.teams.TeamsRepository;
import br.com.nextgen.DGA_DB_MANAGER.repositories.user.UserRepository;
import br.com.nextgen.DGA_DB_MANAGER.service.AuthService;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/members")
@RequiredArgsConstructor //lombok ja cria o construtor para n precisar colocar autowired em cada classe
public class MembersController {


    private final MembersRepository  repository;
    private final TeamsRepository    teamsRepository;
    private final UserRepository     userRepository;
    private final AuthService        authService;


    @GetMapping("/{id_team}/search")
    public ResponseEntity<Page<MembersResponseDTO>> searchByText(
            @PathVariable BigInteger id_team,
            @RequestParam(required = false) String text,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
        ) {


        Account account = authService.getAccount();
            
        Pageable pageable = PageRequest.of(page, size);
        Page<Members> members = repository.findByNameContainingIgnoreCase(text,pageable,account.getId(),id_team);

        if (members.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Page<MembersResponseDTO> response = members.map(member -> 
            new MembersResponseDTO(member.getId(),member.getUser(), member.getTeams(),member.getActive())
        );
    
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Members>  getByID(@PathVariable BigInteger id){

        Members domain = this.repository.findById(id).orElse(null);
        
        if (domain == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(domain);
    }

    @GetMapping()
    public ResponseEntity<List<Members>>  get(@RequestParam String id_team){

        // var all = this.repository.findAll();
        // return ResponseEntity.ok(all);
        System.out.println(id_team);
        List<Members> domain ;

        if (id_team != null) {
            domain = this.repository.findByTeamsId(new BigInteger(id_team)).orElse(null);
        }else{
            return ResponseEntity.notFound().build();
        }
        
        if (domain == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(domain);
    }


    @Transactional
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Validated MembersRequestDTO body){

        User authUser = authService.getAuthUser();

        if (authUser.getId() == null) {
            // Trate o caso em que o userId não pôde ser obtido
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado ou ID não disponível");
        }

        //Account account = authService.getAccount();

        Teams  teams = teamsRepository.findById(body.id_team().toString()).orElseThrow(() -> new RuntimeException("activity not found"));
        User    user = userRepository.findById(body.id_user()).orElseThrow(() -> new RuntimeException("stage not found"));
        

        Members newObj = new Members();
                newObj.setTeams(teams);
                newObj.setUser(user);
                newObj.setActive(body.active());
        
        this.repository.save(newObj);

        return ResponseEntity.ok(newObj);
     
    }

    @Transactional
    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Validated MembersRequestDTO body){

        User authUser = authService.getAuthUser();

        if (authUser.getId() == null) {
            // Trate o caso em que o userId não pôde ser obtido
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado ou ID não disponível");
        }
        
        Members domain = this.repository.findById(body.id()).orElse(null);
        Teams   teams  = teamsRepository.findById(body.id_team().toString()).orElseThrow(() -> new RuntimeException("activity not found"));
        User    user   = userRepository.findById(body.id_user()).orElseThrow(() -> new RuntimeException("stage not found"));
        
        if(domain != null){

            domain.setTeams(teams);
            domain.setUser(user);
            domain.setActive(body.active());

            this.repository.save(domain);

            return ResponseEntity.ok(domain);

        }else{

            return ResponseEntity.notFound().build();
        }
        

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Members>  delete(@PathVariable BigInteger id){
        
        Members domain = this.repository.findById(id).orElse(null);
        
        if (domain == null) {
            return ResponseEntity.notFound().build();
        }
        
        this.repository.delete(domain);

        return ResponseEntity.ok(domain);
    }

}

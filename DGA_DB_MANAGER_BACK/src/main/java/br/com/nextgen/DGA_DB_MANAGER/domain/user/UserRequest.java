package br.com.nextgen.DGA_DB_MANAGER.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRequest {
    
    private String Name;
    private String Email;

}

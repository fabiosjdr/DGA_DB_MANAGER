package br.com.nextgen.DGA_DB_MANAGER;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class DgaDbManagerApplication {

	public static void main(String[] args) {


		Dotenv dotenv = Dotenv.configure().load();
	
        // Configurar variáveis de ambiente
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_NAME", dotenv.get("DB_NAME"));
        System.setProperty("DB_USER", dotenv.get("DB_USER"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		
		SpringApplication.run(DgaDbManagerApplication.class, args);
	}

}

package br.com.nextgen.DGA_DB_MANAGER.service;

import org.springframework.stereotype.Service;

@Service
public class HelloWorldService {

    public String helloWorld(String name){
        return "Frango " + name ;
    }

}
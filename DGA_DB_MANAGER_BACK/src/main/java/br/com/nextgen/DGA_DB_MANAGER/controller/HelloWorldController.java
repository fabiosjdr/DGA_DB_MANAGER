package br.com.nextgen.DGA_DB_MANAGER.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.nextgen.DGA_DB_MANAGER.domain.user.UserRequest;
import br.com.nextgen.DGA_DB_MANAGER.service.HelloWorldService;

@RestController
@RequestMapping("/helloWorld") //path principal

public class HelloWorldController {

    @Autowired //opção 1: autowired //o proprio springboot gerencia a injection
    private HelloWorldService helloWorldService;

    //opção 2: construct auto injection
    // public HelloWorldController(HelloWorldService helloWorldService){
    //     this.helloWorldService = helloWorldService;
    // }

    
    @GetMapping()
    //@GetMapping(/get) exemplo se quiser mudar o path
    public String helloWorldGet(@RequestParam String name){
        return helloWorldService.helloWorld(name);
    }

    @PostMapping("/{id}")
    public String helloWorldPost(@PathVariable String id, @RequestBody UserRequest body){
        return "helloWorldPost "+ id + " - " + body.getName();
    }

}

package com.embarkx.FirstSpring;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class HelloController {

    @GetMapping("/")
    public String helloWorld() {
        return "Hello world";
    }

    @PostMapping("/")
    public String postHello(@RequestBody String name) {
        return "Hello " + name;
    }
    
}

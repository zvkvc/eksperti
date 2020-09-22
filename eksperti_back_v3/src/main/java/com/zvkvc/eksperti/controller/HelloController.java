package com.zvkvc.eksperti.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/")
    String hello() {
        return "Dobrodosli na pocetnu stranu! Za vise informacije o API: /swagger-ui.html# ";
    }
}

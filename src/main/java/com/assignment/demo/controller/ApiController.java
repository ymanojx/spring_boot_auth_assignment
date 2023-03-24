package com.assignment.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/public")
    public ResponseEntity<String> publicApi() {
        return new ResponseEntity<>("public API", HttpStatus.OK);
    }

    @GetMapping("/private")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> privateApi() {
        return new ResponseEntity<>("private API", HttpStatus.OK);
    }
}

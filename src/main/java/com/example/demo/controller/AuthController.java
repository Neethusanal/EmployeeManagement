package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Employee;
import com.example.demo.model.LoginRequest;
import com.example.demo.service.AuthService;

@RestController
@RequestMapping("/api/employees/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {

        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Employee> login(
            @RequestBody LoginRequest loginRequest) {

        Employee employee =
                authService.login(loginRequest);

        return ResponseEntity.ok(employee);
    }
}
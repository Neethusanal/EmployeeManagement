package com.example.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Employee;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.LoginResponse;
import com.example.demo.repository.EmployeeRepository;

@Service
public class AuthService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            EmployeeRepository employeeRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest loginRequest) {

        // Find employee by email
        Employee employee =
                employeeRepository.findByEmail(
                        loginRequest.getEmail());

        if (employee == null) {
            throw new RuntimeException(
                    "Invalid email or password");
        }

        // Only ADMIN can login
        if (employee.getRole() != Employee.Role.ADMIN) {
            throw new RuntimeException(
                    "Only admin can login");
        }

        // Check account status
        if (employee.getStatus() != Employee.Status.ACTIVE) {
            throw new RuntimeException(
                    "Admin account is inactive");
        }

        // Check password
        boolean passwordMatches =
                passwordEncoder.matches(
                        loginRequest.getPassword(),
                        employee.getPassword());

        if (!passwordMatches) {
            throw new RuntimeException(
                    "Invalid email or password");
        }

        // Generate JWT
        String token =
                jwtService.generateToken(employee);

        // Return JWT + employee information
        return new LoginResponse(
                token,
                employee.getEmployeeId(),
                employee.getName(),
                employee.getRole().name()
        );
    }
}
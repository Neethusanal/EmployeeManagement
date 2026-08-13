package com.example.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Employee;
import com.example.demo.model.LoginRequest;
import com.example.demo.repository.EmployeeRepository;

@Service
public class AuthService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            EmployeeRepository employeeRepository,
            PasswordEncoder passwordEncoder) {

        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Employee login(LoginRequest loginRequest) {

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

        return employee;
    }
}
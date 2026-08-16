
package com.example.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            PasswordEncoder passwordEncoder) {

        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // CREATE
    public Employee createEmployee(Employee employee) {

        // Check duplicate email
        Employee existingEmployee =
                employeeRepository.findByEmail(employee.getEmail());

        if (existingEmployee != null) {
            throw new RuntimeException("Email already exists");
        }

        // Generate random UUID employee ID
        String employeeId = UUID.randomUUID().toString();

        employee.setEmployeeId(employeeId);

        // Only ADMIN needs password for login
        if (employee.getRole() == Employee.Role.ADMIN) {

            if (employee.getPassword() == null ||
                employee.getPassword().isBlank()) {

                throw new RuntimeException(
                        "Password is required for admin");
            }

            employee.setPassword(
                    passwordEncoder.encode(employee.getPassword())
            );

        } else {

            // Normal employees don't need login password
            employee.setPassword(null);
        }

        return employeeRepository.save(employee);
    }

    // GET ALL
    public List<Employee> getAllEmployees() {

        return employeeRepository.findAll()
                .stream()
                .filter(employee -> employee.getRole() != Employee.Role.ADMIN)
                .toList();
        
    }    // GET BY ID
    public Employee getEmployeeById(String employeeId) {

        return employeeRepository.findById(employeeId);
    }

    // UPDATE
    public Employee updateEmployee(
            String employeeId,
            Employee employee) {

        // Find existing employee
        Employee existing =
                employeeRepository.findById(employeeId);

        if (existing == null) {
            throw new RuntimeException("Employee not found");
        }

        // Keep the existing UUID
        employee.setEmployeeId(employeeId);

        // Handle password
        if (employee.getRole() == Employee.Role.ADMIN) {

            // If a new password is provided, encode it
            if (employee.getPassword() != null &&
                !employee.getPassword().isBlank()) {

                employee.setPassword(
                        passwordEncoder.encode(employee.getPassword())
                );

            } else {

                // Keep the existing password
                employee.setPassword(existing.getPassword());
            }

        } else {

            // Normal employees don't have a password
            employee.setPassword(null);
        }

        return employeeRepository.update(employee);
    }

    // DELETE
    public void deleteEmployee(String employeeId) {

        Employee existing =
                employeeRepository.findById(employeeId);

        if (existing == null) {
            throw new RuntimeException("Employee not found");
        }

        employeeRepository.delete(employeeId);
    }
}

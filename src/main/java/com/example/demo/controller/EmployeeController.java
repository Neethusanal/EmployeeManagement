package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import com.example.demo.config.CorsConfig;

@RestController
@RequestMapping("/api/employees")

public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // CREATE
    @PostMapping("/{new}")
    public ResponseEntity<Employee> createEmployee(
            @RequestBody Employee employee) {

        Employee savedEmployee = employeeService.createEmployee(employee);

        return ResponseEntity.ok(savedEmployee);
    }

    // READ - all employees
    
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<Employee> employees =
                employeeService.getAllEmployees(page, size);

        return ResponseEntity.ok(employees);
    }
    

    // READ - employee by ID
    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(
            @PathVariable String employeeId) {

        Employee employee = employeeService.getEmployeeById(employeeId);

        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(employee);
    }
    

    // UPDATE
 // UPDATE
    @PutMapping("/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable String employeeId,
            @RequestBody Employee employee) {

        Employee updatedEmployee =
                employeeService.updateEmployee(employeeId, employee);

        return ResponseEntity.ok(updatedEmployee);
    }

    // DELETE
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable String employeeId) {

        employeeService.deleteEmployee(employeeId);

        return ResponseEntity.noContent().build();
    }
}
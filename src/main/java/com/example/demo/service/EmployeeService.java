


package com.example.demo.service;
import java.util.UUID;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // CREATE
    public Employee createEmployee(Employee employee) {

    	employee.setEmployeeId(UUID.randomUUID().toString());
    	


        return employeeRepository.save(employee);
    }

    // READ - all employees
    public List<Employee> getAllEmployees() {

        return employeeRepository.findAll();
    }

    // READ - employee by ID
    public Employee getEmployeeById(String employeeId) {

        return employeeRepository.findById(employeeId);
    }

    // UPDATE
    public Employee updateEmployee(Employee employee) {

        return employeeRepository.update(employee);
    }

    // DELETE
    public void deleteEmployee(String employeeId) {

        employeeRepository.delete(employeeId);
    }
}

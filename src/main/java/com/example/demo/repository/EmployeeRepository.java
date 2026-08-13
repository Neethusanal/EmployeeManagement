package com.example.demo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Employee;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

@Repository
public class EmployeeRepository {

    private final DynamoDbTable<Employee> employeeTable;

    public EmployeeRepository(DynamoDbTable<Employee> employeeTable) {
        this.employeeTable = employeeTable;
    }

    // CREATE
    public Employee save(Employee employee) {

        employeeTable.putItem(employee);

        return employee;
    }

    // READ - by ID
    public Employee findById(String employeeId) {

        Key key = Key.builder()
                .partitionValue(employeeId)
                .build();

        return employeeTable.getItem(key);
    }

    // READ - all
    public List<Employee> findAll() {

        return employeeTable.scan()
                .items()
                .stream()
                .toList();
    }

    // FIND BY EMAIL - used for login
    public Employee findByEmail(String email) {

        return employeeTable.scan()
                .items()
                .stream()
                .filter(employee ->
                        email.equalsIgnoreCase(employee.getEmail()))
                .findFirst()
                .orElse(null);
    }

    // UPDATE
    public Employee update(Employee employee) {

        employeeTable.updateItem(employee);

        return employee;
    }

    // DELETE
    public void delete(String employeeId) {

        Key key = Key.builder()
                .partitionValue(employeeId)
                .build();

        employeeTable.deleteItem(key);
    }
}
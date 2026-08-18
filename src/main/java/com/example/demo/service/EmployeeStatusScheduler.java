package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@Component
public class EmployeeStatusScheduler {

    private final EmployeeRepository employeeRepository;

    public EmployeeStatusScheduler(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void activateEmployees() {

        LocalDate today = LocalDate.now();

        List<Employee> employees = employeeRepository.findAll();

        for (Employee employee : employees) {

            if (employee.isAutoActivationPending()
                    && employee.getJoiningDate() != null
                    && !employee.getJoiningDate().isAfter(today)) {

                employee.setStatus(Employee.Status.ACTIVE);

                employee.setAutoActivationPending(false);

                employeeRepository.update(employee);
            }
        }
    }
}
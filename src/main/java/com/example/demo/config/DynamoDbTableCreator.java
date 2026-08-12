package com.example.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.model.Employee;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

@Configuration
public class DynamoDbTableCreator {

    @Bean
    CommandLineRunner createEmployeeTable(
            DynamoDbTable<Employee> employeeTable) {

        return args -> {

            try {

                employeeTable.createTable();

                System.out.println(
                        "Employees table created successfully!"
                );

            } catch (Exception e) {

                System.out.println(
                        "Employees table already exists or creation failed."
                );

                e.printStackTrace();
            }
        };
    }
}
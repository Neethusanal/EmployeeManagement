package com.example.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DynamoDbConnectionTest {

    @Bean
    CommandLineRunner testDynamoDbConnection(
            DynamoDbClient dynamoDbClient) {

        return args -> {

            try {

                dynamoDbClient.listTables();

                System.out.println(
                        "DynamoDB connected successfully!"
                );

            } catch (Exception e) {

                System.out.println(
                        "DynamoDB connection failed!"
                );

                e.printStackTrace();
            }
        };
    }
}
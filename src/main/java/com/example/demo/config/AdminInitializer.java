//package com.example.demo.config;
//import java.util.UUID;
//
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import com.example.demo.model.Employee;
//import com.example.demo.model.Employee.Role;
//import com.example.demo.model.Employee.Status;
//import com.example.demo.repository.EmployeeRepository;
//
//@Configuration
//public class AdminInitializer {
//
//    @Bean
//    CommandLineRunner createAdmin(EmployeeRepository employeeRepository) {
//
//        return args -> {
//
//            Employee admin = new Employee();
//
//            admin.setEmployeeId(UUID.randomUUID().toString());
//            admin.setName("Admin");
//            admin.setEmail("admin@gmail.com");
//            admin.setPhoneNumber("0000000000");
//            admin.setDepartment("Administration");
//            admin.setDesignation("Administrator");
//            admin.setJoiningDate("2026-08-13");
//            admin.setStatus(Status.ACTIVE);
//            admin.setRole(Role.ADMIN);
//
//            PasswordEncoder encoder = new BCryptPasswordEncoder();
//            admin.setPassword(encoder.encode("admin123"));
//
//            employeeRepository.save(admin);
//
//            System.out.println("Initial admin created!");
//            System.out.println("Email: admin@gmail.com");
//           System.out.println("Password: admin123");
//       };
//    }
//}
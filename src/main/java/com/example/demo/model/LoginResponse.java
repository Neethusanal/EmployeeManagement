package com.example.demo.model;

public class LoginResponse {

    private String token;
    private String employeeId;
    private String name;
    private String role;

    public LoginResponse(
            String token,
            String employeeId,
            String name,
            String role) {

        this.token = token;
        this.employeeId = employeeId;
        this.name = name;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
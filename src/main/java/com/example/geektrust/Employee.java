package com.example.geektrust;

public class Employee {
    private String email;

    public Employee(String email) {
        this.email = email;

    }

    public String getName() {
        return email.substring(0, email.indexOf("@"));
    }

    public String getEmail() {
        return email;
    }
}

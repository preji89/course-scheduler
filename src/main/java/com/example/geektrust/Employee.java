package com.example.geektrust;

public class Employee {
    private String email;
    private String name;


    public Employee(String email) {
        this.email = email;
        this.name = email.substring(0,email.indexOf("@"));
    }


    public String getName() {
        return name;
    }


    public String getEmail() {
        return email;
    }
}

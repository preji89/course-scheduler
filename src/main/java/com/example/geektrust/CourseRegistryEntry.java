package com.example.geektrust;

public class CourseRegistryEntry {
    String courseRegistrationId;
    private Course course;
    private Employee employee;

    public CourseRegistryEntry(String courseRegistrationId,Course course, Employee employee) {
        this.courseRegistrationId =courseRegistrationId;
        this.course = course;
        this.employee = employee;
    }

    public Course getCourse() {
        return course;
    }

    public Employee getEmployee() {
        return employee;
    }
}

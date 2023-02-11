package com.example.geektrust;

import java.text.SimpleDateFormat;
import java.util.*;

public class Course {

    private String courseName;
    private String instructor;
    private Date dateOfStart;
    private int minEmployees;
    private int maxEmployees;
    private boolean isAllotted;
    private List <Employee> registeredEmployees = new ArrayList<>();


    public Course(String courseName, String instructor, Date date, int minEmployees, int maxEmployees) {
        this.courseName = courseName;
        this.instructor = instructor;
        this.dateOfStart = date;
        this.minEmployees = minEmployees;
        this.maxEmployees = maxEmployees;
        this.isAllotted = false;
    }

    public String generateCourseOfferingId(){
        return "OFFERING-"+courseName+"-"+instructor;
    }

    String register(Employee employee) {
        Date today = new Date();
        if (dateOfStart.before(today) && registeredEmployees.size()<minEmployees){
            return "COURSE_CANCELLED";
        }
        if (registeredEmployees.size() >= maxEmployees) {
            return "COURSE_FULL_ERROR";
        }
        registeredEmployees.add(employee);
        return "ACCEPTED";

    }


    String cancel(Employee employee) {
        Date today = new Date();
        if(!isAllotted){
            registeredEmployees.remove(employee);
            return "CANCEL_ACCEPTED";
        }else {
            return "CANCEL_REJECTED";
        }

    }

    public String getCourseName() {
        return courseName;
    }

    public void setAllotted(boolean allotted) {
        isAllotted = allotted;
    }

    public String allotEntry() {
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
        return this.generateCourseOfferingId() + " "
                + courseName+" "
                + instructor+" "
                +formatter.format(dateOfStart);
    }
}

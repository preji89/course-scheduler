package com.example.geektrust;

import java.util.*;
import java.util.stream.Collectors;




public class CourseRegistry {
    List <CourseRegistryEntry> courseRegistrationEntries = new ArrayList<>();

    public String register(Course course, Employee employee) {
        String courseRegistrationStatus = course.register(employee);
        if (courseRegistrationStatus.equals("ACCEPTED")){
            String courseRegistrationId = getCourseRegistrationId(course, employee);
            courseRegistrationEntries.add(new CourseRegistryEntry(courseRegistrationId,course,employee));
            return courseRegistrationId +" "+courseRegistrationStatus;
        }else {
            return courseRegistrationStatus;
        }
    }

    private String getCourseRegistrationId(Course course, Employee employee) {
        return "REG-COURSE-" + employee.getName() + "-" + course.getCourseName();
    }

    public String cancel(String courseRegistrationId){

       CourseRegistryEntry courseRegistrationEntry = courseRegistrationEntries.stream().filter(entry-> entry.courseRegistrationId.equals(courseRegistrationId)).findFirst().get();
       Course course = courseRegistrationEntry.getCourse();
       Employee employee = courseRegistrationEntry.getEmployee();

       String courseCancellationStatus = course.cancel(employee);
        if (courseCancellationStatus.equals("CANCEL_ACCEPTED")){
            courseRegistrationEntries.remove(courseRegistrationEntry);
        }
        return courseRegistrationId+" "+courseCancellationStatus;
    }

    public String allot(Course course) {

        List<CourseRegistryEntry> allottedList = courseRegistrationEntries.stream().filter(entry -> entry.getCourse().equals(course)).collect(Collectors.toList());
        Comparator<CourseRegistryEntry> comparingByCourseRegId = (CourseRegistryEntry e1, CourseRegistryEntry e2) -> e1.courseRegistrationId.compareTo(e2.courseRegistrationId);
        Collections.sort(allottedList, comparingByCourseRegId);
        String allotOutput="";
        for (CourseRegistryEntry allottedEntry :
                allottedList) {
             allotOutput+=allottedEntry.courseRegistrationId + " "
                    + allottedEntry.getEmployee().getEmail() + " "
                    +course.allotEntry()+"\n";

        }
    return allotOutput;

    }
}

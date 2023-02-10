package com.example.geektrust;

import java.util.HashMap;
import java.util.Map;


public class CourseOffering {
    private Map<String,Course> courseOfferings = new HashMap<>();

    public Course getCourseFromCourseOfferingId(String courseOfferingId){
        return courseOfferings.get(courseOfferingId);
    }

    String addCourseOffering(Course course){
      String courseOfferingId = course.generateCourseOfferingId();
      courseOfferings.put(courseOfferingId,course);
      return courseOfferingId;
    }
}

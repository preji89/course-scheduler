package com.example.geektrust;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CommandHandler {

    CourseOffering courseOffering = new CourseOffering();
    CourseRegistry courseRegistry = new CourseRegistry();

    String handleCommand(Command command, String[] params) throws ParseException {
        if (!hasValidParamsCount(command,params)){
            return "INPUT_DATA_ERROR";
        }
        switch (command) {
            case ADD_COURSE_OFFERING: {
                String courseName = params[0];
                String instructor = params[1];
                SimpleDateFormat format = new SimpleDateFormat("ddmmyyyy");
                Date date= format.parse(params[2]);
                int minEmployees = Integer.parseInt(params[3]);
                int maxEmployees = Integer.parseInt(params[4]);
                return courseOffering.addCourseOffering(new Course(courseName,instructor,date,minEmployees,maxEmployees));
            }
            case REGISTER: {
                String email = params[0];
                String courseOfferingId = params[1];
                Employee employee = new Employee(email);
                Course course = courseOffering.getCourseFromCourseOfferingId(courseOfferingId);
                return courseRegistry.register(course,employee);
            }

            case CANCEL: {
                String courseRegistrationId = params[0];
                return courseRegistry.cancel(courseRegistrationId);
            }

            case ALLOT: {
                String courseOfferingId = params[0];
                Course course = courseOffering.getCourseFromCourseOfferingId(courseOfferingId);
                return courseRegistry.allot(course);
            }
            default:
                return "";

        }

    }

    private boolean hasValidParamsCount(Command command, String[] params) {
       return (command.noOfParams() == params.length);
    }
}

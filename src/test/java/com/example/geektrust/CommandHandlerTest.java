package com.example.geektrust;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandHandlerTest {

    CommandHandler commandHandler = new CommandHandler();
    Command addCourseOfferingCommand = Command.ADD_COURSE_OFFERING;
    Command registerCommand = Command.REGISTER;
    Command cancelCommand = Command.CANCEL;
    Command allotCommand = Command.ALLOT;

    @Nested
    class AddCourseOfferingCommandTest {

        @Test
        void shouldReturnCourseOfferingIdWhenCommandIsAddCourseOfferingAndParametersAreCorrect() throws ParseException {

            String[] params = {"PYTHON", "JOHN", "05062023", "1", "3"};

            String expected = "OFFERING-PYTHON-JOHN";
            String actual = commandHandler.handleCommand(addCourseOfferingCommand, params);

            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnInputDataErrorWhenParametersAreInCorrect() throws ParseException {

            String[] params = {"PYTHON", "JOHN", "05062023"};

            String expected = "INPUT_DATA_ERROR";
            String actual = commandHandler.handleCommand(addCourseOfferingCommand, params);

            assertEquals(expected, actual);
        }

    }

    @Nested
    class RegisterCommandTest {
        @BeforeEach
        void addCourseOffering() throws ParseException {

            String[] params = {"JAVA", "JAMES", "16062023", "1", "2"};
            commandHandler.handleCommand(addCourseOfferingCommand, params);

        }

        @Test
        void shouldReturnCourseRegistrationIdAndStatusAsAcceptedWhenCommandIsRegister() throws ParseException {

            String[] params = {"ANDY@GMAIL.COM", "OFFERING-JAVA-JAMES"};

            String expected = "REG-COURSE-ANDY-JAVA ACCEPTED";
            String actual = commandHandler.handleCommand(registerCommand, params);

            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnStatusAsCourseFullErrorWhenCommandIsRegisterAndMaxRegistrationsAreCompleted() throws ParseException {

            String[] params1 = {"WOO@GMAIL.COM", "OFFERING-JAVA-JAMES"};
            String[] params2 = {"FOO@GMAIL.COM", "OFFERING-JAVA-JAMES"};
            String[] params3 = {"BAR@GMAIL.COM", "OFFERING-JAVA-JAMES"};

            String expected = "COURSE_FULL_ERROR";
            commandHandler.handleCommand(registerCommand, params1);
            commandHandler.handleCommand(registerCommand, params2);
            String actual = commandHandler.handleCommand(registerCommand, params3);

            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnStatusAsCourseCancelledWhenCommandIsRegisterAndMinEmployeesNotRegisteredAfterStartDate() throws ParseException {
            String[] params = {"JAVA", "JOHN", "15062020", "1", "2"};
            commandHandler.handleCommand(addCourseOfferingCommand, params);

            String[] params1 = {"WOO@GMAIL.COM", "OFFERING-JAVA-JOHN"};
            String expected = "COURSE_CANCELLED";

            String actual = commandHandler.handleCommand(registerCommand, params1);

            assertEquals(expected, actual);
        }

    }

    @Nested
    class AllotCommandTest {
        @BeforeEach
        void addCourseOfferingAndRegistrations() throws ParseException {
            String[] params1 = {"PYTHON", "JOHN", "05062023", "1", "3"};
            commandHandler.handleCommand(addCourseOfferingCommand, params1);
            String[] params2 = {"ANDY@GMAIL.COM", "OFFERING-PYTHON-JOHN"};
            commandHandler.handleCommand(registerCommand, params2);
            String[] params3 = {"WOO@GMAIL.COM", "OFFERING-PYTHON-JOHN"};
            commandHandler.handleCommand(registerCommand, params3);
            String[] params4 = {"BOBY@GMAIL.COM", "OFFERING-PYTHON-JOHN"};
            commandHandler.handleCommand(registerCommand, params4);
        }

        @Test
        void shouldReturnAllottedEmployeesAndStatusAsConfirmedWhenCommandIsAllot() throws ParseException {
            String[] params = {"OFFERING-PYTHON-JOHN"};

            String expected = "REG-COURSE-ANDY-PYTHON ANDY@GMAIL.COM OFFERING-PYTHON-JOHN PYTHON JOHN 05062023 CONFIRMED\n" +
                            "REG-COURSE-BOBY-PYTHON BOBY@GMAIL.COM OFFERING-PYTHON-JOHN PYTHON JOHN 05062023 CONFIRMED\n" +
                            "REG-COURSE-WOO-PYTHON WOO@GMAIL.COM OFFERING-PYTHON-JOHN PYTHON JOHN 05062023 CONFIRMED\n";
            String actual = commandHandler.handleCommand(allotCommand, params);

            assertEquals(expected, actual);
        }

        @Test
        void shouldNotReturnCancelledRegistrationsInAllot() throws ParseException {
            String[] params1 = {"REG-COURSE-BOBY-PYTHON"};
            commandHandler.handleCommand(cancelCommand, params1);
            String[] params = {"OFFERING-PYTHON-JOHN"};

            String expected = "REG-COURSE-ANDY-PYTHON ANDY@GMAIL.COM OFFERING-PYTHON-JOHN PYTHON JOHN 05062023 CONFIRMED\n" +
                    "REG-COURSE-WOO-PYTHON WOO@GMAIL.COM OFFERING-PYTHON-JOHN PYTHON JOHN 05062023 CONFIRMED\n";
            String actual = commandHandler.handleCommand(allotCommand, params);

            assertEquals(expected, actual);
        }

    }

    @Nested
    class CancelCommandTest {
        @BeforeEach
        void addCourseOfferingAndRegistration() throws ParseException {
            String[] params1 = {"PYTHON", "JOHN", "05062023", "1", "3"};
            commandHandler.handleCommand(addCourseOfferingCommand, params1);
            String[] params2 = {"BOBY@GMAIL.COM", "OFFERING-PYTHON-JOHN"};
            commandHandler.handleCommand(registerCommand, params2);
        }

        @Test
        void shouldReturnStatusAsCancelAcceptedWhenCourseHasNotBeenAllotted() throws ParseException {
            String[] params = {"REG-COURSE-BOBY-PYTHON"};

            String expected = "REG-COURSE-BOBY-PYTHON CANCEL_ACCEPTED";
            String actual = commandHandler.handleCommand(cancelCommand, params);

            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnStatusAsCancelRejectedWhenCourseHasBeenAllotted() throws ParseException {
            String[] params1 = {"OFFERING-PYTHON-JOHN"};
            commandHandler.handleCommand(allotCommand, params1);


            String[] params = {"REG-COURSE-BOBY-PYTHON"};

            String expected = "REG-COURSE-BOBY-PYTHON CANCEL_REJECTED";
            String actual = commandHandler.handleCommand(cancelCommand, params);

            assertEquals(expected, actual);
        }

    }


}

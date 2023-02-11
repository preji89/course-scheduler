package com.example.geektrust;

import java.util.Arrays;
import java.util.Optional;

public enum Command {
    ADD_COURSE_OFFERING("ADD-COURSE-OFFERING",5),
    REGISTER("REGISTER",2),
    CANCEL("CANCEL",1),
    ALLOT("ALLOT",1);

    private final String text;
    private int expectedNoOfParams;


    Command(String text, int expectedNoOfParams) {
        this.text = text;
        this.expectedNoOfParams = expectedNoOfParams;
    }
    public int noOfParams (){
        return expectedNoOfParams;
    }
    public static Optional<Command> getCommandFromText(String text) {
        return Arrays.stream(values())
                .filter(command -> command.text.equals(text))
                .findFirst();
    }

}

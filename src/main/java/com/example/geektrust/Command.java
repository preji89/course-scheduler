package com.example.geektrust;

import java.util.Arrays;
import java.util.Optional;

public enum Command {
    ADD_COURSE_OFFERING("ADD-COURSE-OFFERING",5),
    REGISTER("REGISTER",2),
    CANCEL("CANCEL",1),
    ALLOT("ALLOT",1);

    private final String text;
    private int noOfParams;

    Command(String text, int noOfParams) {
        this.text = text;
        this.noOfParams = noOfParams;
    }
    public int noOfParams (){
        return noOfParams;
    }
    public static Optional<Command> getCommandFromText(String text) {
        return Arrays.stream(values())
                .filter(command -> command.text.equals(text))
                .findFirst();
    }

}

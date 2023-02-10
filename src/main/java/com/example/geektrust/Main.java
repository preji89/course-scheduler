package com.example.geektrust;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)  {
        try {
            FileInputStream fis = new FileInputStream(args[0]);
            Scanner sc = new Scanner(fis);
            CommandHandler commandHandler = new CommandHandler();
            StringBuilder output = new StringBuilder();
            while (sc.hasNextLine()) {
               String[] input=sc.nextLine().split(" ");
               Optional<Command> command = Command.getCommandFromText(input[0]);
               String[] params = Arrays.copyOfRange(input, 1, input.length);
               if (command.isPresent()){
                   output.append(commandHandler.handleCommand(command.get(),params)+"\n");
               }else{
                   output.append("Invalid Command!\n");
               }

            }
            sc.close();
            System.out.println(output.toString());
        } catch (IOException | ParseException e) {
            System.err.println(e);
        }

	}
}

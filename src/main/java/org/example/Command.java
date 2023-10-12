package org.example;

import java.util.InputMismatchException;

public enum Command {
    HELP("HELP", "Μoves are introduced in the form of \"A5\", \"C13\" etc.\n\nAvailable Commands:"),
    SAVE("SAVE", "Saves the game in its current state"),
    LOAD("LOAD", "Loads a saved game"),
    EXIT("EXIT", "Terminates the game");

    private final String commandString;
    private final String helpText;

    Command(String commandString, String helpText) {
        this.commandString = commandString;
        this.helpText = helpText;
    }

    public String getCommandString() {
        return commandString;
    }

    public String getHelpText() {
        return helpText;
    }

    public Command fromString(String commandString) {
        if(commandString.equalsIgnoreCase("help") ) {
            return Command.HELP;
        }
        if(commandString.equalsIgnoreCase("save") ) {
            return Command.SAVE;
        }
        if(commandString.equalsIgnoreCase("load") ) {
            return Command.LOAD;
        }
        if(commandString.equalsIgnoreCase("exit") ) {
            return Command.EXIT;
        }
        throw new InputMismatchException();
    }


}

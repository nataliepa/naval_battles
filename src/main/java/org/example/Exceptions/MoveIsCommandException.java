package org.example.Exceptions;

import org.example.Command;

public class MoveIsCommandException extends InvalidLocationException {
    Command command;

    public MoveIsCommandException(Command cmd) {
        command = cmd;
    }

    public Command getCommand() {
        return command;
    }
}

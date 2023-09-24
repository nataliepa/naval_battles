package org.example.players;

import java.util.InputMismatchException;

public enum PlayerControl {
    HUMAN,
    COMPUTER;

    public PlayerControl fromString(String playerControl) throws InputMismatchException {
        if(playerControl.matches("[H|h]\\w*")){
            return PlayerControl.HUMAN;
        }
        if (playerControl.matches("[C|c]\\w*")){
            return PlayerControl.COMPUTER;
        }
        throw new InputMismatchException();
    }
}

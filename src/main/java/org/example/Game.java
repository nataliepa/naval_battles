package org.example;

import org.example.Exceptions.InvalidLocationException;
import org.example.Exceptions.MoveIsCommandException;
import org.example.players.ComputerPlayer;
import org.example.players.HumanPlayer;
import org.example.players.Player;
import org.example.players.PlayerControl;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Random;

public class Game {
    private int rowsNum;
    private int columnsNum;
    private int movesNum;
    private Player player1;
    private Player player2;

    public int getRowsNum() {
        return rowsNum;
    }

    public void setRowsNum(int rowsNum) {
        this.rowsNum = rowsNum;
    }

    public int getColumnsNum() {
        return columnsNum;
    }

    public void setColumnsNum(int columnsNum) {
        this.columnsNum = columnsNum;
    }

    public int getMovesNum() {
        return movesNum;
    }

    public void setMovesNum(int movesNum) {
        this.movesNum = movesNum;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public int initFieldDimensions(Scanner in, String dimension) {
        while(true) {
            System.out.println("Enter number of " + dimension + "[10-15]");
            try {
                int number = Integer.parseInt(in.nextLine());
                if(number < 10 || number > 15) {
                    throw new InputMismatchException();
                }
                return number;
            } catch (NumberFormatException e) {
                System.out.println("Input is not an integer");
            } catch (InputMismatchException e) {
                System.out.println("The number must be between 10 and 15");
            }
        }
    }
    public void init(Scanner in) {

        System.out.println("Welcome to Naval Games!");

        rowsNum = initFieldDimensions(in, "rows");
        columnsNum = initFieldDimensions(in, "columns");

        player1 = initPlayer(1, in, rowsNum, columnsNum);

        player2 = initPlayer(2, in, rowsNum, columnsNum);

        System.out.println("Choose number of moves [or 0 if the game will be played until end]");

        while (true) {
            try {
                movesNum = in.nextInt();
                break;
            } catch (InputMismatchException e) {
                in.nextLine();
                System.out.println("Wrong input. Enter an integer.");
            }
        }
    }

    private Player initPlayer(int playerNum, Scanner in, int rowsNum, int columnsNum) {
        Player player;
        PlayerControl playerControl = PlayerControl.HUMAN;

        System.out.println("Player " + playerNum + ":" );

        System.out.println("Enter your name: ");
        String name = in.nextLine();

        while (true) {
            try {
                System.out.println("Choose between Human and Computer");
                String control = in.nextLine();
                if(playerControl.fromString(control) == playerControl)
                {
                    player = new HumanPlayer(name, 0 ,null);
                } else {
                    player = new ComputerPlayer(name, 0 , null);
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Wrong input! Choose between [Human/Computer]");
            }
        }
        player.initField(rowsNum, columnsNum);

        return player;
    }

    public void placeShips() {
        player1.placeShips(player2.getField());
        player2.placeShips(player1.getField());
    }

    public void play() {
        Player player;
        Random rand = new Random();

        int randomValue = rand.nextInt(2);
        System.out.println("Random value = " + randomValue);

        while (movesNum != 0) {
            if(randomValue == 0) {
                player = player1;
                randomValue = 1;
            } else {
                player = player2;
                randomValue = 0;
            }

            System.out.println(player.getName() + " it's your turn!");
            System.out.println(player.getField().toStringWithShips());
            while(true) {
                try {
                    Location location = player.selectMove();
                    System.out.println(location.getColumn()+ " " + location.getRow());
                    if(!player.getField().getLocation(location.getRow(), location.getColumn()).isMarked()) {
                        player.getField().processValidMove(location);
                        break;
                    } else {
                        System.out.println("You have already chosen this location in a previous move");
                    }
                } catch (MoveIsCommandException e) {
                    System.out.println(e.getCommand());
                } catch (InvalidLocationException ignored) {}
            }



            movesNum--;
        }
    }

    public void showResult() {}

}

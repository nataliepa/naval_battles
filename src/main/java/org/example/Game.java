package org.example;

import org.example.Exceptions.InvalidLocationException;
import org.example.Exceptions.MoveIsCommandException;
import org.example.players.ComputerPlayer;
import org.example.players.HumanPlayer;
import org.example.players.Player;
import org.example.players.PlayerControl;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Random;


public class Game implements Serializable {
    private int rowsNum;
    private int columnsNum;
    private int movesNum;
    private Player player1;
    private Player player2;
    private Player currentPlayer;

    private boolean gameLoadedFromFile = false;


    public int initFieldDimensions(Scanner in, String dimension) {
        while(true) {
            System.out.print("Enter number of " + dimension + "[10-15]: ");
            try {
                int number = Integer.parseInt(in.next());
                if(number < 10 || number > 15) {
                    throw new InputMismatchException();
                }
                return number;
            } catch (NumberFormatException e) {
                System.out.println(TextColor.RED.getTextColor() + "Input is not an integer" + TextColor.RESET.getTextColor());
            } catch (InputMismatchException e) {
                System.out.println(TextColor.RED.getTextColor() + "The number must be between 10 and 15" + TextColor.RESET.getTextColor());
            }
        }
    }
    public void init(Scanner in) {
        System.out.println(TextColor.CYAN.getTextColor() + "\n\n-------------------------");
        System.out.println(" Welcome to Naval Games!");
        System.out.println("-------------------------\n" + TextColor.RESET.getTextColor());

        System.out.println(TextColor.GREEN.getTextColor() + "Let's initialize the field dimensions!\n" + TextColor.RESET.getTextColor());
        rowsNum = initFieldDimensions(in, "rows");
        columnsNum = initFieldDimensions(in, "columns");

        player1 = initPlayer(1, in, rowsNum, columnsNum);

        player2 = initPlayer(2, in, rowsNum, columnsNum);

        int modeSelection;

        while (true) {
            try {
                System.out.println("\n" + TextColor.GREEN.getTextColor() + "Choose how the game will be played:\n" + TextColor.RESET.getTextColor());
                System.out.println("1. Till the end\n" + "2. Completed in a certain number of moves");
                modeSelection = Integer.parseInt(in.next());
                if(modeSelection != 1 && modeSelection !=2) throw new InputMismatchException();
                break;
            } catch (NumberFormatException e) {
                System.out.println(TextColor.RED.getTextColor() + "Input is not an integer" + TextColor.RESET.getTextColor());
            } catch (InputMismatchException e) {
                System.out.println(TextColor.RED.getTextColor() + "Input must be between 1 or 2" + TextColor.RESET.getTextColor());
            }
        }

        System.out.println(modeSelection);

        if(modeSelection == 1) movesNum = -1;
        else {
            while (true) {
                try {
                    System.out.print("\nEnter number of moves: ");
                    movesNum = Integer.parseInt(in.next());
                    if(movesNum < 0) throw new InputMismatchException();
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(TextColor.RED.getTextColor() + "Input is not an integer" + TextColor.RESET.getTextColor());
                } catch (InputMismatchException e) {
                    System.out.println(TextColor.RED.getTextColor() + "Wrong input! Number of moves must be a positive number!" + TextColor.RESET.getTextColor());
                }
            }
        }

    }

    private Player initPlayer(int playerNum, Scanner in, int rowsNum, int columnsNum) {
        Player player;
        PlayerControl playerControl = PlayerControl.HUMAN;

        System.out.println("\n" + TextColor.BLUE.getTextColor() + "Player " + playerNum + ":" + TextColor.RESET.getTextColor());

        System.out.print("Enter your name: ");
        String name = in.next();

        while (true) {
            try {
                System.out.print("Choose between Human and Computer: ");
                String control = in.next();
                if(playerControl.fromString(control) == playerControl)
                {
                    player = new HumanPlayer(name, 0 ,null);
                } else {
                    player = new ComputerPlayer(name, 0 , null);
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println(TextColor.RED.getTextColor() + "Wrong input!" + TextColor.RESET.getTextColor());
            }
        }
        player.initField(rowsNum, columnsNum);
        player.getField().setPlayer(player);

        return player;
    }

    public void placeShips() {
        System.out.println(TextColor.BLUE.getTextColor() + "\nPlayer 1:" + TextColor.RESET.getTextColor());
        player1.placeShips(player2.getField());
        System.out.println(TextColor.BLUE.getTextColor() + "\nPlayer 2:" + TextColor.RESET.getTextColor());
        player2.placeShips(player1.getField());
    }

    public boolean commandToBeExecuted(Command command) {
        Scanner in = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("Are you sure you want to " + command.getCommandString() + " the game? [y/n]");
                String answer = in.next();
                if (!answer.matches("[Y|y]") && !answer.matches("[N|n]")) {
                    throw new InputMismatchException();
                }
                return answer.equalsIgnoreCase("y");
            } catch (InputMismatchException e) {
                System.out.println(TextColor.RED.getTextColor() + "Wrong Input..." + TextColor.RESET.getTextColor());
            }
        }
    }

    public void executeHelpCommand() {
        for(Command c : Command.values()) {
            if(c == Command.HELP) {
                System.out.println(c.getHelpText());
            } else {
                System.out.println(c.getCommandString() + ": " + c.getHelpText());
            }
            System.out.println();
        }
    }

    public void executeSaveCommand() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("gameFile.txt"))) {
            oos.writeObject(this);
            System.out.println("Game successfully saved");
        } catch (IOException e) {
            System.out.println("An error occurred...");
        }
    }

    public void executeLoadCommand() {

        try (ObjectInputStream oi = new ObjectInputStream(new FileInputStream("gameFile.txt"))) {
            Game game =  (Game) oi.readObject();

            this.movesNum = game.movesNum;
            this.player1 = game.player1;
            this.player2 = game.player2;
            this.currentPlayer = game.currentPlayer;
            gameLoadedFromFile = true;
            play();
        } catch (IOException e) {
            System.out.println("An error occurred...");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void executeExitCommand() {
        System.out.println("Exiting the game...");
        System.exit(0);
    }

    public void executeCommand(Command command) {
        if(command == Command.HELP) {
            executeHelpCommand();
        } else if(command == Command.SAVE) {
            executeSaveCommand();
        } else if(command == Command.LOAD) {
            executeLoadCommand();
        } else {
            executeExitCommand();
        }
    }

    public void playRound(Player player) {
        currentPlayer = player;
        System.out.println(TextColor.YELLOW.getTextColor() + player.getName() + " it's your turn!\n" + TextColor.RESET.getTextColor());
        System.out.println(player.getField().toStringWithShips());
        while(true) {
            try {
                Location location = player.selectMove();
                System.out.println(location.getColumn()+ " " + location.getRow());
                if(player.getField().getLocation(location.getRow(), location.getColumn()).isMarked()) {
                    System.out.println("You have already chosen this location in a previous move");
                } else {
                    player.getField().processValidMove(location);
                    System.out.println(TextColor.GREEN.getTextColor() + player.getField().toStringWithShips() + TextColor.RESET.getTextColor());
                    break;
                }
            } catch (MoveIsCommandException e) {
                if(commandToBeExecuted(e.getCommand())) {
                    executeCommand(e.getCommand());
                    if(e.getCommand() == Command.LOAD) break;
                }
            } catch (InvalidLocationException ignored) {}
        }
        if(player.hasWon()) {
            showResult();
            System.exit(0);
        }
    }


    public void play() {

        Player player;

        if(!gameLoadedFromFile) {
            Random rand = new Random();
            player = rand.nextInt(2) == 0 ? player1 : player2;
        } else {
            player = currentPlayer;
        }

        int roundNum = 1;

        while (true) {
            System.out.println(TextColor.BLUE.getTextColor() + "\n----------");
            System.out.println(" Round " + roundNum++);
            System.out.println("----------\n" + TextColor.RESET.getTextColor());


            playRound(player);
            playRound(player == player1 ? player2 : player1);


            if(movesNum > 0) {
                movesNum--;
            }

           if(movesNum == 0) {
                showResult();
                System.exit(0);
           }
        }
    }

    public void showResult() {
        System.out.println("\n\n---------------------------------");
        if(player1.getScore() == player2.getScore()) {
            System.out.println("Game ended in a draw!");
            System.out.println("---------------------------------");
        } else {
            Player player = player1.getScore() > player2.getScore() ? player1 : player2;
            System.out.println(player.getName() + " is the winner");
            System.out.println("---------------------------------");
        }

        System.out.println("Final results: ");
        System.out.println(player1.getName() + ": " + player1.getScore() + " points");
        System.out.println(player2.getName() + ": " + player2.getScore() + " points");
        System.out.println("---------------------------------");
    }

}

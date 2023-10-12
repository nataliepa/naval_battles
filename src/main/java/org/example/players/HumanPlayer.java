package org.example.players;

import org.example.Command;
import org.example.Exceptions.InvalidLocationException;
import org.example.Exceptions.MoveIsCommandException;
import org.example.Field;
import org.example.Location;
import org.example.TextColor;
import org.example.ships.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class HumanPlayer extends Player{
    public HumanPlayer(String name, int score, Field field) {
        super(name, score, field);
    }

    public static final int AUTOMATICALLY = 1;
    public static final int MANUALLY = 2;

    @Override
    public void placeShips(Field otherField) {
        Scanner in = new Scanner(System.in);
        int choice;

        while(true) {
            System.out.println(TextColor.YELLOW.getTextColor() + field.getPlayer().getName() + TextColor.RESET.getTextColor() +
                    " choose how you want to place your ships\n1. Automatically\n2. Manually");
            try {
                choice = Integer.parseInt(in.next());
                if (choice != AUTOMATICALLY && choice != MANUALLY) throw new InputMismatchException();
                break;
            } catch (NumberFormatException e) {
                System.out.println(TextColor.RED.getTextColor() + "Input is not an integer" + TextColor.RESET.getTextColor());
            } catch (InputMismatchException e) {
                System.out.println(TextColor.RED.getTextColor() + "Input must be between 1 or 2" + TextColor.RESET.getTextColor());
            }
        }

        ArrayList<Ship> ships = new ArrayList<>(7);

        ships.add(new AircraftCarrier(otherField, null, null));
        ships.add(new AircraftCarrier(otherField, null, null));
        ships.add(new Destroyer(otherField, null, null));
        ships.add(new Destroyer(otherField, null, null));
        ships.add(new Destroyer(otherField, null, null));
        ships.add(new Submarine(otherField, null, null));
        ships.add(new Submarine(otherField, null, null));

        if(choice == AUTOMATICALLY) {
            for(Ship ship : ships) {
                otherField.placeShipRandomly(ship, 0, false);
            }
        } else {
            for(Ship ship : ships) {
                System.out.println("You are about to place a(n) " + ship.getClass().getSimpleName());

                while (true) {
                    System.out.println("Enter the bow's position");
                    String bowPosition = in.next();

                    try {
                        Location location = otherField.getLocation(bowPosition);
                        System.out.println("row = " + location.getRow());
                        System.out.println("column = " + location.getColumn());
                        System.out.println(bowPosition);
                        ship.setStart(location);

                        while(true) {
                            try {
                                System.out.println("Enter ship's direction [HORIZONTAL/VERTICAL]");
                                String direction = in.next();
                                ship.setDir(ShipDirection.fromString(direction));
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Wrong input");
                            }
                        }
                        if(otherField.placeShip(ship, false)) {
                            System.out.println(otherField.toStringWithShips());
                            break;
                        }
                    } catch (InvalidLocationException e) {
                        System.out.println("Invalid Location...");
                    }

                }
            }
        }
    }


    @Override
    public Location selectMove() throws InvalidLocationException {
        String move;
        Scanner in = new Scanner(System.in);
        Command moveCommand = Command.HELP;

        System.out.print("Enter your move: ");
        move = in.next();

        try {
            if(moveCommand.fromString(move) == Command.HELP || moveCommand.fromString(move) == Command.LOAD
                    || moveCommand.fromString(move) == Command.SAVE || moveCommand.fromString(move) == Command.EXIT) {
                throw new MoveIsCommandException(moveCommand.fromString(move));
            }
        } catch (InputMismatchException ignored) {}

        return field.getLocation(move);

    }
}

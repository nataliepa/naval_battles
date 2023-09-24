package org.example.players;

import org.example.Field;
import org.example.ships.AircraftCarrier;
import org.example.ships.Destroyer;
import org.example.ships.Ship;
import org.example.ships.Submarine;

import java.util.ArrayList;
import java.util.Scanner;

public class HumanPlayer extends Player{
    public HumanPlayer(String name, int score, Field field) {
        super(name, score, field);
    }

    @Override
    public void placeShips(Field otherField) {
        Scanner in = new Scanner(System.in);
        int choice;

        System.out.println("Choose how you want to place your ships [0:automatically/1:randomly]");
        while(true) {
            try {
                do{
                    choice = Integer.parseInt(in.nextLine());
                }while(choice < 0 || choice > 1);
                break;
            } catch (Exception e) {
                System.out.println("Wrong input! Enter an integer!");
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

        for(Ship ship : ships) {
            otherField.placeShipRandomly(ship, 0, false);
        }

    }
}

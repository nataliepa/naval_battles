package org.example.players;

import org.example.Field;
import org.example.Location;
import org.example.ships.AircraftCarrier;
import org.example.ships.Destroyer;
import org.example.ships.Ship;
import org.example.ships.Submarine;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ComputerPlayer extends Player{
    public ComputerPlayer(String name, int score, Field field) {
        super(name, score, field);
    }

    @Override
    public void placeShips(Field otherField) {
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

    @Override
    public Location selectMove() {
        Random rand = new Random();

        return field.getLocation(rand.nextInt(field.getNumRows()), rand.nextInt(field.getNumCols()));
    }
}

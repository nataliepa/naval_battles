package org.example.ships;

import org.example.Field;
import org.example.Location;

public class Submarine extends Ship{

    public Submarine(Field field, Location start, ShipDirection dir) {
        super(1, 3, 'S', field, start, dir);
    }

}

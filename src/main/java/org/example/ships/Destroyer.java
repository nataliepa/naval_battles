package org.example.ships;

import org.example.Field;
import org.example.Location;

public class Destroyer extends Ship{

    public Destroyer(Field field, Location start, ShipDirection dir) {
        super(3, 2, 'D', field, start, dir);
    }

    @Override
    public String getSinkMessage() {
        return "You sank a Destroyer";
    }
}

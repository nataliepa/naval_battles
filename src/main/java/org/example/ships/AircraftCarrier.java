package org.example.ships;

import org.example.Field;
import org.example.Location;

public class AircraftCarrier extends Ship{
    public AircraftCarrier(Field field, Location start, ShipDirection dir) {
        super(5, 5, 'A', field, start, dir);
    }

}

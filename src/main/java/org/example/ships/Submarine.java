package org.example.ships;

import org.example.Field;
import org.example.Location;

public class Submarine extends Ship{

    public Submarine(Field field, Location start, ShipDirection dir) {
        super(1, 3, 'S', field, start, dir);
    }

    @Override
    public String getSinkMessage() {
        return "You sank a Submarine!";
    }

    @Override
    public String getHitMessage() {
        return "You hit a Submarine!";
    }

    @Override
    public void threaten() {
        if(!this.isHit()) {
            field.removeShip(this);
            field.placeShipRandomly(this, 0, true);
        }
    }
}

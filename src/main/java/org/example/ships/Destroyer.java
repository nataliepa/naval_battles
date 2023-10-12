package org.example.ships;

import org.example.Field;
import org.example.Location;

public class Destroyer extends Ship{

    public Destroyer(Field field, Location start, ShipDirection dir) {
        super(3, 2, 'D', field, start, dir);
    }

    @Override
    public String getSinkMessage() {
        return "You sank a Destroyer!";
    }

    @Override
    public String getHitMessage() {
        return "You hit a Destroyer!";
    }

    @Override
    public void threaten() {
        Location previousLocation = this.getStart();
        ShipDirection previousShipDirection = this.getDir();

        if(!this.isHit()) {
            field.removeShip(this);

            if(!field.placeShipRandomly(this, 1, true)) {
                this.setStart(previousLocation);
                this.setDir(previousShipDirection);
                System.out.println("Ship could not be placed to another location");
                field.placeShip(this, false);
            }
        }
    }
}

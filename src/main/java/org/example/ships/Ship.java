package org.example.ships;

import org.example.Field;
import org.example.Location;

public abstract class Ship {
    private final int shipLength;
    private final int points;
    private final char letter;
    private final Field field;
    private Location start;
    private ShipDirection dir;

    public Ship (int shipLength, int points, char letter, Field field, Location start, ShipDirection dir) {
        this.shipLength = shipLength;
        this.points = points;
        this.letter = letter;
        this.field = field;
        this.start = start;
        this.dir = dir;
    }

    public int getShipLength() {
        return shipLength;
    }

    public int getPoints() {
        return points;
    }

    public char getLetter() {
        return letter;
    }

    public Field getField() {
        return field;
    }

    public Location getStart() {
        return start;
    }

    public void setStart(Location start) {
        this.start = start;
    }

    public ShipDirection getDir() {
        return dir;
    }

    public void setDir(ShipDirection dir) {
        this.dir = dir;
    }

    public void hit(){}

    public boolean isHit() {
        return true;
    }

    public boolean isSinking() {
        return true;
    }

    public String getHitMessage() {
        return "";
    }

    public void threaten() {}

}

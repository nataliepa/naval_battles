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
    private int hits = 0;

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

    public void hit(){
        hits++;
    }

    public boolean isHit() {
        return hits > 0;
    }

    public boolean isSinking() {
        return hits == shipLength;
    }

    public String getHitMessage() {
        return "You hit a ship!";
    }

    public abstract String getSinkMessage();

    public void threaten() {}

}
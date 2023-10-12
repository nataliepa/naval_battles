package org.example.ships;

import org.example.Field;
import org.example.Location;

import java.io.Serializable;

public abstract class Ship implements Serializable {
    private final int shipLength;
    private final int points;
    private final char letter;
    protected final Field field;
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

    public abstract String getHitMessage();

    public abstract String getSinkMessage();

    public abstract void threaten();

}

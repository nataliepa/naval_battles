package org.example;

import org.example.ships.Ship;

import java.io.Serializable;

public class Location implements Serializable {
    private int row;
    private int column;
    private Ship ship;
    private boolean marked;

    public Location() {}

    public Location(int row, int column, Ship ship, boolean marked) {
        this.row = row;
        this.column = column;
        this.ship = ship;
        this.marked = marked;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public void mark() {
        marked = true;
        if(ship != null) {
            ship.hit();
        }
    }

    public boolean isMarked() {
        return marked;
    }

    public boolean isEmpty() {
        return ship == null;
    }

    public boolean isHit() {
        return ship != null && isMarked();
    }

}

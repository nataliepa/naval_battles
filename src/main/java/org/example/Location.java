package org.example;

import org.example.ships.Ship;

public class Location {
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

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public void mark() {

    }

    public boolean isMarked() {
        return marked;
    }

    public boolean isEmpty() {
        return ship == null;
    }

    public boolean isHit() {
        return true;
    }

}

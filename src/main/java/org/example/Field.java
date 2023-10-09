package org.example;

import org.example.Exceptions.InvalidLocationException;
import org.example.players.Player;
import org.example.ships.Ship;
import org.example.ships.ShipDirection;

import java.util.Random;

public class Field {
    private int numRows;
    private int numCols;
    private Location[][] locations;
    private Player player;

    public Field() {}

    public Field(int numRows, int numCols, Player player) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.player = player;
        locations = new Location[numRows][numCols];
        for(int i=0; i<numRows; i++) {
            for(int j=0; j<numCols; j++) {
                locations[i][j] = new Location(i, j, null, false);
            }
        }
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    public Location[][] getLocations() {
        return locations;
    }

    public void setLocations(Location[][] locations) {
        this.locations = locations;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Location getLocation(int r, int c) {
        return locations[r][c];
    }

    public Location getLocation(String locString) throws InvalidLocationException {

        if(!locString.matches("^[a-zA-Z]\\d{1,2}$")) {
            System.out.println("Input cannot be translated to location..");
            throw new InvalidLocationException();
        }

        int row = locString.toUpperCase().charAt(0) - 'A';
        int column = Integer.parseInt(locString.substring(1));

        if(row < 0 || row >= numRows) {
            System.out.println("Row is out of bounds");
            throw new InvalidLocationException();
        }

        if(column < 1 || column > numCols) {
            System.out.println("Column is out of bounds");
            throw new InvalidLocationException();
        }

        return locations[row][column - 1];
    }

    public boolean placeShipRandomly(Ship s, int maxTries, boolean checkMarked) {
        Random rand = new Random();
        boolean placed = false;
        int tries = 0;
        // TODO: set ship direction

        while(true){
            s.setStart(new Location(rand.nextInt(numRows), rand.nextInt(numCols), s, false));
            s.setDir(rand.nextInt(2) == 0 ? ShipDirection.HORIZONTAL : ShipDirection.VERTICAL);
            placed = placeShip(s, checkMarked);
            tries++;

            if(placed) return true;
            if(maxTries !=0 && tries > maxTries) return false;
        }

    }

    public boolean placeShip(Ship s, boolean checkMarked) {

        if(checkMarked && s.isHit()) {
            System.out.println("Ship cannot be moved.");
            return false;
        }

        if(s.getDir() == ShipDirection.HORIZONTAL) {
            // check if the whole ship can fit in the table
            if(s.getStart().getColumn() + s.getShipLength() > numRows) {
                System.out.println("Ship can not be placed. Horizontally out of bounds.");
                return false;
            }
            // check if there is no other ship in these locations
            for (int i = s.getStart().getColumn(); i < s.getStart().getColumn() + s.getShipLength(); i++) {
                if (!locations[s.getStart().getRow()][i].isEmpty()) {
                    return false;
                }
            }
            // place ship
            for (int i = s.getStart().getColumn(); i < s.getStart().getColumn() + s.getShipLength(); i++) {
                locations[s.getStart().getRow()][i].setShip(s);
            }
        } else {
            // check if the whole ship can fit in the table
            if(s.getStart().getRow() + s.getShipLength() > numCols) {
                System.out.println("Ship can not be placed. Vertically out of bounds.");
                return false;
            }
            // check if there is no other ship in these locations
            for (int i = s.getStart().getRow(); i < s.getStart().getRow() + s.getShipLength(); i++) {
                if (!locations[i][s.getStart().getColumn()].isEmpty()) {
                    return false;
                }
            }
            // place ship
            for (int i = s.getStart().getRow(); i < s.getStart().getRow() + s.getShipLength(); i++) {
                locations[i][s.getStart().getColumn()].setShip(s);
            }
        }

        System.out.println("Ship is placed!");
        return true;
    }

    public void removeShip(Ship s) {

        if(s.getDir() == ShipDirection.HORIZONTAL) {
            for (int i = s.getStart().getColumn(); i < s.getStart().getColumn() + s.getShipLength(); i++) {
                locations[s.getStart().getRow()][i].setShip(null);
            }
        } else {
            for (int i = s.getStart().getRow(); i < s.getStart().getRow() + s.getShipLength(); i++) {
                locations[i][s.getStart().getColumn()].setShip(null);
            }
        }
    }

    public void processValidMove(Location moveLoc) {
        moveLoc.mark();

        if(!moveLoc.isEmpty()){

            if(moveLoc.isHit()) {
                System.out.println(moveLoc.getShip().getHitMessage());
            }

            if(moveLoc.getShip().getHits() == moveLoc.getShip().getShipLength()) {
                System.out.println(moveLoc.getShip().getSinkMessage());
                player.setScore(player.getScore() + moveLoc.getShip().getPoints());
            }
        }


        // TODO: check if a ship is threaten
    }

    @Override
    public String toString() {
        StringBuilder field  = new StringBuilder();

        field.append("    ");
        for(int i=0; i<numRows; i++) {
            field.append(i + 1 + "  ");
        }

        field.append("\n");
        field.append("   ");
        for(int i=0; i<numRows; i++) {
            field.append("---");
        }
        field.append("\n");

        char c = 'A';

        for(int i=0; i<numRows; i++) {
            field.append(c++ + " | ");
            for(int j=0; j<numCols; j++) {
                if (!locations[i][j].isMarked()) {
                    field.append(".  ");
                } else {
                    if (locations[i][j].getShip() == null) {
                        field.append("o ");
                    } else {
                        field.append("x ");
                    }
                }
            }
            field.append("\n");
        }

        return field.toString();
    }

    public String toStringWithShips() {
        StringBuilder field  = new StringBuilder();

        field.append("    ");
        for(int i=0; i<numRows; i++) {
            field.append(i + 1).append("  ");
        }

        field.append("\n");
        field.append("   ");
        field.append("---".repeat(Math.max(0, numRows)));
        field.append("\n");

        char c = 'A';

        for(int i=0; i<numRows; i++) {
            field.append(c++).append(" | ");
            for(int j=0; j<numCols; j++) {
                if (!locations[i][j].isMarked()) {
                    if (locations[i][j].getShip() == null) {
                        field.append(".  ");
                    } else {
                        field.append(locations[i][j].getShip().getLetter()).append("  ");
                    }
                } else {
                    if (locations[i][j].getShip() == null) {
                        field.append("o  ");
                    } else {
                        field.append("x").append(locations[i][j].getShip().getLetter()).append(" ");
                    }
                }
            }
            field.append("\n");
        }

        return field.toString();
    }
}

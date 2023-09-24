package org.example;

import org.example.players.Player;
import org.example.ships.AircraftCarrier;
import org.example.ships.Ship;

import java.util.Arrays;
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
                locations[i][j] = new Location(numRows, numCols, null, false);
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

    public boolean placeShipRandomly(Ship s, int maxTries, boolean checkMarked) {
        Random rand = new Random();

        int randomRow;
        int randomColumn;
        int shipDirection; // 0: Horizontal 1: Vertical
        boolean canBePlaced;

        while(true) {
            randomRow = rand.nextInt(numRows);
            randomColumn = rand.nextInt(numCols);
            shipDirection = rand.nextInt(2);
            canBePlaced = true;
            // place ship horizontally
            if (shipDirection == 0) {
                // try to place ship horizontally (right)
                if (randomColumn + s.getShipLength() < numCols) {
                    for (int j = randomColumn; j < randomColumn + s.getShipLength(); j++) {
                        if (!locations[randomRow][j].isEmpty()) {
                            canBePlaced = false;
                        }
                    }
                    if (canBePlaced) {
                        s.setStart(new Location(randomRow, randomColumn, s, false));
                        for (int j = randomColumn; j < randomColumn + s.getShipLength(); j++) {
                            locations[randomRow][j].setShip(s);
                        }
                        return true;
                    }
                }

            } else {
                // try to place ship vertically (top-down)
                if (randomRow + s.getShipLength() < numRows) {
                    for (int j = randomRow; j < randomRow + s.getShipLength(); j++) {
                        if (!locations[j][randomColumn].isEmpty()) {
                            canBePlaced = false;
                        }
                    }
                    if (canBePlaced) {
                        s.setStart(new Location(randomRow, randomColumn, s, false));
                        for (int j = randomRow; j < randomRow + s.getShipLength(); j++) {
                            locations[j][randomColumn].setShip(s);
                        }
                        return true;
                    }
                }

            }
        }
    }

    public boolean placeShip(Ship s, boolean checkMarked) {

        return true;
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
                } else if (locations[i][j].isMarked()) {
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
                if (!locations[i][j].isEmpty()) {
                    field.append(locations[i][j].getShip().getLetter()+ "  ");
                } else {
                    field.append(".  ");
                }
            }
            field.append("\n");
        }

        return field.toString();
    }
}

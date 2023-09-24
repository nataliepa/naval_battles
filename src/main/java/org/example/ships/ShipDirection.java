package org.example.ships;
import java.util.InputMismatchException;

/**
 * Ships Directions
 * {@link #HORIZONTAL}
 * {@link #VERTICAL}
 *
 * Contains a method that takes
 * a String as a parameter that
 * represents the ship's direction
 * and returns the corresponding
 * ShipDirection object
 *
 * @author N. Panagou
 */
public enum ShipDirection {
    /**
     * Horizontal Direction
     */
    HORIZONTAL,
    /**
     * Vertical Direction
     */
    VERTICAL;

    public ShipDirection fromString(String dirString) {
        if(dirString.matches("[H|h]\\w*")) {
            return ShipDirection.HORIZONTAL;
        } else if((dirString.matches("[V|v]\\w*"))) {
            return  ShipDirection.VERTICAL;
        } else {
            throw new InputMismatchException();
        }
    }
}

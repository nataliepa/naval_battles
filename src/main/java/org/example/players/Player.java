package org.example.players;

import org.example.Field;
import org.example.Location;

public abstract class Player {
    private String name;
    private int score;
    Field field;
    public Player() {}
    public Player(String name, int score, Field field) {
        this.name = name;
        this.score = score;
        this.field = field;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void initField(int r, int c) {
        field = new Field(r, c, null);
    }

    public abstract void placeShips(Field otherField);

    public boolean hasWon() {
        return true;
    }

    public Location selectMove() {
        return new Location();
    }
}

package org.example;

public enum TextColor {
    CYAN("\u001B[36m"),
    YELLOW("\u001B[33m"),
    RED("\u001B[31m"),
    RESET("\u001B[0m"),
    BLUE("\u001B[34m"),
    GREEN("\u001B[32m"),
    PURPLE("\u001B[35m");

    private final String textColor;
    TextColor(String textColor) {
        this.textColor = textColor;
    }
    public String getTextColor() {
        return textColor;
    }
}

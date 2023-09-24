package org.example;


import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Game game = new Game();
        Scanner in = new Scanner(System.in);

        game.init(in);

        System.out.println(game.getMovesNum());
        game.play();


    }
}

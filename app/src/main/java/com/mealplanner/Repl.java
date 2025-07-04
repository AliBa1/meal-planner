package com.mealplanner;

import java.util.Scanner;

public class Repl {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("meal-planner > ");
            String input = scanner.nextLine();
            input = input.trim().toLowerCase();
            switch (input) {
                case "help":
                    System.out.println("Meal Planner Commands");
                    System.out.println("print dishes: view all saved dishes");
                    System.out.println("add dish: adds a dish to the list of dishes");
                    System.out.println();
                case "print dishes":

                    break;

                default:
                    System.out.println("That command doesn't exist! Try again.");
                    break;
            }
        }
    }
}

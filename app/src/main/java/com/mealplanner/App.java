package com.mealplanner;

import java.util.Scanner;

public class App {
    Dish[] dishes;

    public static void main(String[] args) {
        Dish testDish1 = new Dish("Burrito");
        Dish testDish2 = new Dish("Burger");
        Dish testDish3 = new Dish("Taco");

        App app = new App();
        app.dishes = new Dish[] { testDish1, testDish2, testDish3 };
        // app.printDishes();
        app.repl();
    }

    public void repl() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("meal-planner > ");
            if (!scanner.hasNextLine()) {
                break; // no more input
            }
            String input = scanner.nextLine();
            input = input.trim().toLowerCase();
            switch (input) {
                case "exit":
                    return;
                case "help":
                    System.out.println("Meal Planner Commands");
                    System.out.println("print dishes: view all saved dishes");
                    System.out.println("add dish: adds a dish to the list of dishes");
                    System.out.println();
                    break;
                case "print dishes":
                    printDishes();
                    break;

                default:
                    System.out.println("That command doesn't exist! Try again.");
                    break;
            }
        }
        scanner.close();
    }

    public void printDishes() {
        System.out.println("Dishes");
        System.out.println("------------");
        for (Dish dish : dishes) {
            System.out.println(dish.name);
        }
        System.out.println("");
    }

}

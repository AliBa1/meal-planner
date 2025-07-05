package com.mealplanner;

import java.util.ArrayList;
import java.util.Scanner;

public class App {
    private Database db;
    private Scanner scanner = new Scanner(System.in);

    public App(boolean isTest) {
        if (isTest) {
            db = new Database(true);
        } else {
            db = new Database(false);
        }
    }

    public static void main(String[] args) {
        App app;
        if (args.length > 0 && args[0].equals("test")) {
            app = new App(true);
        } else {
            app = new App(false);
        }

        app.repl();
    }

    public void repl() {
        while (true) {
            System.out.print("meal-planner > ");
            if (!scanner.hasNextLine()) {
                break;
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
                case "add dish":
                    System.out.print("Dish Name: ");
                    String dishName = scanner.nextLine();
                    dishName = dishName.trim();

                    addDish(dishName);
                    break;

                default:
                    System.out.println("That command doesn't exist! Try again.");
                    break;
            }
        }
        scanner.close();
    }

    public boolean addDish(String name) {
        Dish newDish = new Dish(name);
        return db.addDish(newDish);
    }

    public void printDishes() {
        ArrayList<Dish> dishes = db.getAllDishes();

        if (dishes.isEmpty()) {
            System.out.println("You have not added any dishes");
            System.out.println("Use the 'add dish' command to add a dish");
            return;
        }

        System.out.println("Dishes");
        System.out.println("------------");
        for (Dish dish : dishes) {
            System.out.println(dish.getName());
        }
        System.out.println("");
    }

}

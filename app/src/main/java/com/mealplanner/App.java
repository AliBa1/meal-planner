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
                    System.out.println("get dish: gets a dish by name and prints it");
                    System.out.println("edit dish: edit a dish");
                    System.out.println();
                    break;
                case "print dishes":
                    printDishes();
                    break;
                case "add dish":
                    String dishName = getDishName();

                    addDish(dishName);
                    break;
                case "get dish":
                    dishName = getDishName();

                    Dish retrievedDish = db.getDishByName(dishName);
                    retrievedDish.print();
                    break;
                case "edit dish":
                    dishName = getDishName();

                    Dish oldDish = db.getDishByName(dishName);
                    oldDish.print();

                    System.out.print("New Dish Name: ");
                    String newDishName = scanner.nextLine();
                    newDishName = newDishName.trim();
                    Dish newDish = new Dish(newDishName);
                    db.updateDish(oldDish, newDish);
                    break;
                default:
                    System.out.println("That command doesn't exist! Try again.");
                    break;
            }
        }
        scanner.close();
    }

    private String getDishName() {
        System.out.print("Dish Name: ");
        String dishName = scanner.nextLine();
        dishName = dishName.trim();
        return dishName;
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

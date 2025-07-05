package com.mealplanner;

import java.util.ArrayList;
import java.util.Scanner;

public class App {
    private Database db = new Database();
    private Scanner scanner = new Scanner(System.in);

    public App() {
    }

    public static void main(String[] args) {
        App app = new App();

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
                    addDish();
                    break;

                default:
                    System.out.println("That command doesn't exist! Try again.");
                    break;
            }
        }
        scanner.close();
    }

    public void addDish() {
        System.out.print("Dish Name: ");
        String dishName = scanner.nextLine();
        dishName = dishName.trim();

        Dish newDish = new Dish(dishName);
        db.addDish(newDish);
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

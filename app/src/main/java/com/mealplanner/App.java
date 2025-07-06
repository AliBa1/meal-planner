package com.mealplanner;

import java.util.ArrayList;
import java.util.Arrays;
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

                    // System.out.println("[quantity] [unit] [name] (ex: 0.75 cups Milk) ");
                    // System.out.println("Ingredient: ");

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
                    String newDishName = scanner.nextLine().trim();
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
        String dishName = scanner.nextLine().trim();
        return dishName;
    }

    // chatgpt version
    public boolean addDish(String name) {
        Dish newDish = new Dish(name);

        System.out.println("Enter ingredients (submit with an empty name to finish)");
        System.out.println("Available units: " + Arrays.toString(Unit.values()));

        while (true) {
            System.out.print("Ingredient Name: ");
            String ingredientName = scanner.nextLine().trim();
            if (ingredientName.isEmpty()) {
                break;
            }

            float quantity = 0;
            while (true) {
                System.out.print("Quantity (ex: 0.5): ");
                String quantityInput = scanner.nextLine().trim();
                try {
                    quantity = Float.parseFloat(quantityInput);
                    if (quantity <= 0) {
                        System.out.println("Please enter a positive number.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number. Try again.");
                }
            }

            Unit unit = null;
            while (true) {
                System.out.print("Unit (ex: grams, cups): ");
                String unitInput = scanner.nextLine().trim().toUpperCase();
                try {
                    unit = Unit.valueOf(unitInput);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid unit. Choose from: " + Arrays.toString(Unit.values()));
                }
            }

            Ingredient ingredient = new Ingredient(ingredientName);
            ingredient.setQuantity(quantity);
            ingredient.setUnit(unit);

            newDish.addIngredient(ingredient);
            System.out.println("✓ Ingredient added.\n");
        }

        boolean success = db.addDish(newDish);
        if (success) {
            System.out.println("Dish added successfully!");
        } else {
            System.out.println("Failed to add dish.");
        }
        return success;
    }

    // public boolean addDish(String name) {
    // Dish newDish = new Dish(name);
    //
    // System.out.println("Enter ingredients (submit with an empty name to
    // finish)");
    //
    // while (true) {
    // System.out.print("Ingredient Name: ");
    // String ingredientName = scanner.nextLine().trim();
    // if (ingredientName.isEmpty()) {
    // break;
    // }
    //
    // System.out.print("Quantity (ex: 0.5): ");
    // float quantity = scanner.nextFloat();
    // scanner.nextLine();
    //
    // System.out.print("Unit (ex: grams, cups): ");
    // String unitInput = scanner.nextLine().trim().toUpperCase();
    // Unit unit = Unit.valueOf(unitInput);
    //
    // Ingredient ingredient = new Ingredient(ingredientName);
    // ingredient.setQuantity(quantity);
    // ingredient.setUnit(unit);
    //
    // newDish.addIngredient(ingredient);
    // }
    // return db.addDish(newDish);
    // }

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

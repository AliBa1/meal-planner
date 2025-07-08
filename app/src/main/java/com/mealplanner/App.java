package com.mealplanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class App {
    private final Database db;
    private final Scanner scanner = new Scanner(System.in);

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
                case "exit" -> {
                    return;
                }
                case "help" -> {
                    System.out.println("Meal Planner Commands");
                    System.out.println("print dishes: view all saved dishes");
                    System.out.println("add dish: adds a dish to the list of dishes");
                    System.out.println("get dish: gets a dish by name and prints it");
                    System.out.println("edit dish: edit a dish");
                    System.out.println("del dish: delete a dish");
                    System.out.println();
                }
                case "print dishes" -> {
                    printDishes();
                }
                case "add dish" -> {
                    addDish();
                }
                case "get dish" -> {
                    getDish();
                }
                case "edit dish" -> {
                    editDish();
                }
                case "del dish" -> {
                    deleteDish();
                }
                default -> {
                    System.out.println("That command doesn't exist! Try again.");
                }
            }
        }
        scanner.close();
    }

    private String promptDishName() {
        System.out.print("Dish Name: ");
        String dishName = scanner.nextLine().trim();
        return dishName;
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

    public boolean addDish() {
        String dishName = promptDishName();
        Dish newDish = new Dish(dishName);

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

            Ingredient ingredient = new Ingredient(ingredientName, unit, quantity);
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

    private void getDish() {
        String dishName = promptDishName();
        Dish retrievedDish = db.getDishByName(dishName);
        if (retrievedDish == null) {
            System.out.println("The dish '" + dishName + "' doesn't exist!");
            return;
        }
        retrievedDish.print();
    }

    private void editDish() {
        String dishName = promptDishName();
        Dish oldDish = db.getDishByName(dishName);
        oldDish.print();

        // TODO: ask to edit name or ingredients

        System.out.print("New Dish Name: ");
        String newDishName = scanner.nextLine().trim();
        Dish newDish = new Dish(newDishName);
        db.updateDish(oldDish, newDish);
    }

    private void deleteDish() {
        String dishName = promptDishName();
        Dish dishToDelete = db.getDishByName(dishName);
        dishToDelete.print();

        System.out.print("Are you sure you want to delete this dish? (Y/n): ");
        String userDeleteSelection = scanner.nextLine().trim();
        if (!userDeleteSelection.equals("Y")) {
            return;
        }

        db.deleteDish(dishToDelete);
    }
}

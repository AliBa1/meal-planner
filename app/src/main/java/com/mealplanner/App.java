package com.mealplanner;

import java.util.ArrayList;
import java.util.Scanner;

public class App {
    private ArrayList<Dish> dishes;

    public App() {
        this.dishes = new ArrayList<>();
    }

    public static void main(String[] args) {
        App app = new App();

        app.dishes.add(new Dish("Burrito"));
        app.dishes.add(new Dish("Burger"));
        app.dishes.add(new Dish("Taco"));

        app.repl();
    }

    public void repl() {
        Scanner scanner = new Scanner(System.in);
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
            System.out.println(dish.getName());
        }
        System.out.println("");
    }

}

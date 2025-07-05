package com.mealplanner;

public class Dish {
    private String name;
    // private Recipe recipe;
    // private Intgredient[] ingredients;

    public String getName() {
        return this.name;
    }

    public Dish(String name) {
        this.name = name;
    }

    public void print() {
        System.out.println(this.name);
        System.out.println();
    }
}

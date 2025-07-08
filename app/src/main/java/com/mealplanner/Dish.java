package com.mealplanner;

import java.util.ArrayList;

public class Dish {
    private String name;
    // private Recipe recipe;
    private final ArrayList<Ingredient> ingredients;

    public String getName() {
        return this.name;
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    public ArrayList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public Dish(String name) {
        this.name = name;
        this.ingredients = new ArrayList<>();
    }

    public Dish() {
        this.ingredients = new ArrayList<>();
    }

    public Dish(String name, ArrayList<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public void print() {
        System.out.println(this.name);
        System.out.println("Ingredients");
        System.out.println("-----------------");
        for (Ingredient ingredient : this.ingredients) {
            ingredient.print();
        }
        System.out.println();
    }

    public boolean equals(Dish dish) {
        if (dish.ingredients.size() != this.ingredients.size()) {
            return false;
        }

        for (int i = 0; i < this.ingredients.size(); i++) {
            Ingredient thisIngredient = this.ingredients.get(i);
            Ingredient comparingDishIngredient = dish.ingredients.get(i);
            if (!comparingDishIngredient.equals(thisIngredient)) {
                return false;
            }
        }

        return dish.name.equals(this.name);
    }
}

package com.mealplanner;

import java.util.ArrayList;

public class Dish {
    private int id;
    private String name;
    // private Recipe recipe;
    private ArrayList<Ingredient> ingredients;

    public void setID(int id) {
        this.id = id;
    }

    public int getID() {
        return this.id;
    }

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
        this.ingredients = new ArrayList<Ingredient>();
    }
    
    public Dish(String name, ArrayList<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public void print() {
        System.out.println(this.name);
        System.out.println("Ingredients: ");
        for (Ingredient ingredient : this.ingredients) {
            ingredient.print();
        }
        System.out.println();
    }
}

package com.mealplanner;

import java.text.DecimalFormat;

public class Ingredient {
    private String name;
    private Unit unit;
    private float quantity;

    public Ingredient() {
    }

    public Ingredient(String name) {
        this.name = name;
    }

    public Ingredient(String name, Unit unit, float quantity) {
        this.name = name;
        this.unit = unit;
        this.quantity = quantity;
    }

    public String getName() {
        return this.name;
    }

    public String getUnit() {
        return this.unit.toString();
    }

    public float getQuantity() {
        return this.quantity;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public void print() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        if (this.unit == Unit.COUNT) {
            System.out.println(decimalFormat.format(this.quantity) + " " + this.name);
        } else {
            System.out.println(
                    decimalFormat.format(this.quantity) +
                            " " +
                            this.unit.toString().toLowerCase() +
                            "(s) of " +
                            this.name);
        }
    }

    public boolean equals(Ingredient ingredient) {
        return ingredient.name.equals(this.name) && ingredient.unit == this.unit
                && ingredient.quantity == this.quantity;
    }
}

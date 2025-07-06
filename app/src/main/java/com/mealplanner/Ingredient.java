package com.mealplanner;

public class Ingredient {
    private String name;
    private Unit unit;
    private float quantity;

    public Ingredient(String name) {
        this.name = name;
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
        if (this.unit == Unit.ITSELF) {
            System.out.println(this.quantity + " " + this.name);
        } else {
            System.out.println(this.quantity + " " + this.unit.toString().toLowerCase() + "'s of " + this.name);
        }
    }
}

package com.mealplanner;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
    private Connection connection;
    private Statement statement;

    public Database(boolean isForTesting) {
        try {
            if (isForTesting) {
                File testDatabaseFile = new File("testdb.db");
                if (testDatabaseFile.exists()) {
                    testDatabaseFile.delete();
                }

                connection = DriverManager.getConnection("jdbc:sqlite:testdb.db");
            } else {
                connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            }
            statement = connection.createStatement();

            statement.execute(
                    "CREATE TABLE IF NOT EXISTS dishes (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT UNIQUE)");

            statement.execute(
                    "CREATE TABLE IF NOT EXISTS ingredients (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "dish_id INTEGER," +
                            "name TEXT," +
                            "quantity REAL," +
                            "unit TEXT," +
                            "FOREIGN KEY(dish_id) REFERENCES dishes(id))");
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public boolean addDish(Dish dish) {
        try {
            statement.execute(String.format("INSERT INTO dishes (name) VALUES ('%s')", dish.getName()));
            int dishId = this.getDishByName(dish.getName()).getID();
            for (Ingredient ingredient : dish.getIngredients()) {
                statement.execute(String.format(
                        "INSERT INTO ingredients (dish_id, name, quantity, unit) VALUES ('%d', '%s', '%f', '%s')",
                        dishId, ingredient.getName(), ingredient.getQuantity(), ingredient.getUnit()));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return false;
        }
        return true;
    }

    public boolean updateDish(Dish oldDish, Dish updatedDish) {
        try {
            statement.execute(String.format("UPDATE dishes SET name = '%s' WHERE name = '%s'", updatedDish.getName(),
                    oldDish.getName()));
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return false;
        }
        return true;
    }

    public Dish getDishByName(String name) {
        try {
            // ResultSet result = statement.executeQuery(String.format("SELECT * FROM dishes
            // WHERE name = '%s'", name));
            ResultSet result = statement
                    .executeQuery(String.format("SELECT * FROM dishes WHERE name LIKE '%%%s%%'", name));
            if (result.next()) {
                int id = result.getInt("id");
                String resultName = result.getString("name");
                Dish dish = new Dish(resultName);
                dish.setID(id);

                ResultSet ingredientsResult = statement
                        .executeQuery(String.format("SELECT * FROM ingredients WHERE dish_id = '%f'", (float) id));
                while (ingredientsResult.next()) {
                    String ingredientName = ingredientsResult.getString("name");
                    float quantity = ingredientsResult.getFloat("quantity");
                    String unitString = ingredientsResult.getString("unit");
                    Unit unit = Unit.valueOf(unitString);
                    Ingredient ingredient = new Ingredient(ingredientName);
                    ingredient.setQuantity(quantity);
                    ingredient.setUnit(unit);
                    dish.addIngredient(ingredient);
                }
                return dish;
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return null;
    }

    public ArrayList<Dish> getAllDishes() {
        ArrayList<Dish> dishes = new ArrayList<>();
        try {
            ResultSet result = statement.executeQuery("SELECT * FROM dishes");
            while (result.next()) {
                Dish dish = new Dish(result.getString("name"));
                dishes.add(dish);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return dishes;
    }
}

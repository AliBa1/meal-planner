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

            statement.execute("CREATE TABLE IF NOT EXISTS dishes (name TEXT PRIMARY KEY)");
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public boolean addDish(Dish dish) {
        try {
            statement.execute(String.format("INSERT INTO dishes (name) VALUES ('%s')", dish.getName()));
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return false;
        }
        return true;
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

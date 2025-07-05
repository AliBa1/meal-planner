package com.mealplanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
    private Connection connection;
    private Statement statement;

    public Database() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            statement = connection.createStatement();

            statement.execute("CREATE TABLE IF NOT EXISTS dishes (name TEXT PRIMARY KEY)");
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public void addDish(Dish dish) {
        try {
            statement.execute(String.format("INSERT INTO dishes (name) VALUES ('%s')", dish.getName()));
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
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

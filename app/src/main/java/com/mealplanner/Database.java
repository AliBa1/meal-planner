package com.mealplanner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;


public class Database {
    private ObjectMapper mapper = new ObjectMapper();
    private File file;

    public Database(boolean isForTesting) {
        try {
            if (isForTesting) {
                File testDatabaseFile = new File("testdb.json");
                if (testDatabaseFile.exists()) {
                    testDatabaseFile.delete();
                }

                this.file = testDatabaseFile;
            } else {
                this.file = new File("dishes.json");
            }

            if (file.length() == 0) {
                FileWriter writer = new FileWriter(file);
                writer.write("[]");
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    public boolean addDish(Dish dish) {
        try {
            ArrayList<Dish> dishes = getAllDishes();
            dishes.add(dish);
            mapper.writeValue(file, dishes);
        } catch (IOException e) {
            e.printStackTrace(System.err);
            return false;
        }
        return true;
    }

    public boolean updateDish(Dish oldDish, Dish updatedDish) {
        try {
            // statement.execute(String.format("UPDATE dishes SET name = '%s' WHERE name =
            // '%s'", updatedDish.getName(),
            // oldDish.getName()));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return false;
        }
        return true;
    }

    public Dish getDishByName(String name) {
        try {
            ArrayList<Dish> dishes = getAllDishes();
            for (Dish dish : dishes) {
                if (dish.getName().equals(name)) {
                    return dish;
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return null;
    }

    public ArrayList<Dish> getAllDishes() {
        try {
            ArrayList<Dish> dishes = new ArrayList<>(Arrays.asList(mapper.readValue(file, Dish[].class)));
            return dishes;
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return new ArrayList<Dish>();
    }
}

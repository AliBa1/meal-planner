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

    public Database(boolean testing) {
        try {
            if (testing) {
                File testDatabaseFile = new File("testdb.json");
                if (testDatabaseFile.exists()) {
                    testDatabaseFile.delete();
                }

                this.file = testDatabaseFile;
            } else {
                this.file = new File("dishes.json");
            }

            if (file.length() == 0) {
                try (FileWriter writer = new FileWriter(file);) {
                    writer.write("[]");
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace(System.err);
                }
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
            boolean removed = deleteDish(oldDish);
            if (!removed) {
                return false;
            }

            ArrayList<Dish> dishes = getAllDishes();
            dishes.add(updatedDish);
            mapper.writeValue(file, dishes);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return false;
        }
        return true;
    }

    public boolean deleteDish(Dish dishToDelete) {
        try {
            ArrayList<Dish> dishes = getAllDishes();
            boolean removed = dishes.removeIf(dish -> dish.equals(dishToDelete));

            if (!removed) {
                return false;
            }

            mapper.writeValue(file, dishes);
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
                if (dish.getName().equalsIgnoreCase(name)) {
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
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        return new ArrayList<>();
    }
}

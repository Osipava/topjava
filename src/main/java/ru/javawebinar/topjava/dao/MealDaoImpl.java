package ru.javawebinar.topjava.dao;


import ru.javawebinar.topjava.model.Meal;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class MealDaoImpl implements MealDao {

    private Map<Integer, Meal> meals = new ConcurrentHashMap<>();


    private AtomicInteger count;

    @Override
    public Meal getById(int id) {
        for (Integer i : meals.keySet())
            if (i == id) {
                return meals.get(i);
            }
        return null;
    }

    @Override
    public Map<Integer, Meal> getAll() {
        return meals;
    }

    @Override
    public void delete(int id) {
        meals.remove(id);
    }

    @Override
    public void edit(Meal meal) {
        meals.remove(meal.getId());
        meals.put(meal.getId(), meal);

    }

    @Override
    public void create(Meal meal) {
        count = new AtomicInteger(meals.size());
            meal.setId(count.get());
        meals.put(meal.getId(), meal);


    }


}

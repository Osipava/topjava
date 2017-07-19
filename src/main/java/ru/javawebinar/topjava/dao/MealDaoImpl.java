package ru.javawebinar.topjava.dao;


import ru.javawebinar.topjava.model.Meal;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class MealDaoImpl implements MealDao {

    private Map<Integer, Meal> meals = new ConcurrentHashMap<>();


    private AtomicInteger count = new AtomicInteger(0);

    @Override
    public Meal getById(int id) {
        return meals.get(id);
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

        meals.replace(meal.getId(), meal);

    }

    @Override
    public void create(Meal meal) {
        meal.setId(count.decrementAndGet());
        meals.put(meal.getId(), meal);


    }


}

package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.util.List;
import java.util.Map;

public interface MealDao {
    Meal getById(int id);

    Map<Integer, Meal> getAll();

    void delete(int id);

    void edit(Meal meal);

    void create(Meal meal);

}

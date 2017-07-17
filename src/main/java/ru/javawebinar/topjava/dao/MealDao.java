package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.util.List;

public interface MealDao {
    Meal getMealById(int id);

    List<MealWithExceed> getAllMealWithExceeds();

    void delete(int id);

    void edit(Meal meal);

    void create(Meal meal);

    void updateMealsWithExceeded();

}

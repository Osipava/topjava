package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

public interface MealService {
    Meal save(Meal meal, int userId);

    void delete(int userId, int mealId);

    Meal get(int userId, int mealId);

    Collection<Meal> getAll(int userId);

    void update(Meal meal, int userId);

    Collection<Meal> getFilterDate(LocalDate startDate, LocalDate endDate);

}
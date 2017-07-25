package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

public interface MealRepository {
    Meal save(Meal meal, int userId);

    void delete(int userId, int mealId);

    Meal get(int userId, int mealId);

    Collection<Meal> getAll(int userId);

    Collection<Meal> getFilterDate(LocalDate startDate, LocalDate endDate);

}

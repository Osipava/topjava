package ru.javawebinar.topjava.dao;


import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class MealDaoImpl implements MealDao {

    private List<Meal> meals = new CopyOnWriteArrayList<>();

    {
        meals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        meals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        meals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        meals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        meals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        meals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));

        for (int i = 1; i <= meals.size(); i++)
            meals.get(i - 1).setId(i);
    }

    private final int count = meals.size() + 1;
    private List<MealWithExceed> mealWithExceeds;

    @Override
    public Meal getMealById(int id) {
        for (Meal m : meals)
            if (m.getId() == id) {
                return m;
            }
        return null;
    }

    @Override
    public List<MealWithExceed> getAllMealWithExceeds() {
        updateMealsWithExceeded();
        return mealWithExceeds;
    }

    @Override
    public void delete(int id) {
        meals.remove(getMealById(id));
        updateMealsWithExceeded();

    }

    @Override
    public void edit(Meal meal) {
        meals.remove(meal.getId() - 1);
        meals.add(meal);
        updateMealsWithExceeded();
    }

    @Override
    public void create(Meal meal) {

        meal.setId(count);
        meals.add(meal);
        updateMealsWithExceeded();

    }

    public void updateMealsWithExceeded() {
        int caloriesPerDay = 2000;
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        mealWithExceeds = meals.stream()
                .map(meal -> createWithExceed(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
        Collections.sort(mealWithExceeds, new Comparator<MealWithExceed>() {
            @Override
            public int compare(MealWithExceed lhs, MealWithExceed rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return lhs.getId() < rhs.getId() ? -1 : (lhs.getId() > rhs.getId()) ? 1 : 0;
            }
        });
    }


    public MealWithExceed createWithExceed(Meal meal, boolean exceeded) {
        return new MealWithExceed(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceeded);
    }


}

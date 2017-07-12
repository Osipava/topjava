package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.*;
import java.util.*;


/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();

    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExceed> list = new ArrayList<>();
        HashMap<LocalDate, Integer> mapUserMealPerDay = init(mealList);

        for (UserMeal userMeal : mealList) {
            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                list.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), mapUserMealPerDay.getOrDefault(userMeal.getDateTime().toLocalDate(), mapUserMealPerDay.get(userMeal.getDateTime().toLocalDate())) > caloriesPerDay));
            }
        }
        return list;
    }


    private static HashMap<LocalDate, Integer> init(List<UserMeal> mealList) {

        HashMap<LocalDate, Integer> mapUserMealPerDay = new HashMap<>();
        for (UserMeal userMeal : mealList) {
            mapUserMealPerDay.merge(userMeal.getDateTime().toLocalDate(), userMeal.getCalories() ,(value, newValue) -> value+newValue);

        }
        return mapUserMealPerDay;
    }
}

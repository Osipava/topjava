package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {


            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
                meal.setUserId(userId);
            }

            repository.put(meal.getId(), meal);
            return meal;
    }

    @Override
    public boolean delete(int userId, int mealId) {
        if (mealId == 0 || repository.get(mealId).getUserId() != userId)
            return false;

        repository.remove(mealId);
        return true;


    }

    @Override
    public Meal get(int userId, int mealId) {
        try {
            return repository.get(mealId);
        } catch (NullPointerException e) {
            return null;
        }

    }

    @Override
    public Collection<Meal> getAll(int userId) {

        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());

    }

    @Override
    public Collection<Meal> getFilterDate(LocalDate startDate, LocalDate endDate) {
        return repository.values().stream()
                .filter(meal -> DateTimeUtil.isBetween(repository.get(meal.getId()).getDate(), startDate, endDate))
                .collect(Collectors.toList());

    }

}


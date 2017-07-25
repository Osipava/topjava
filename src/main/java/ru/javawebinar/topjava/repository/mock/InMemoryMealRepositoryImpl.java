package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
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
        MealsUtil.MEALS.forEach(meal -> save(meal,1));
    }

    @Override
    public Meal save(Meal meal, int userId) {

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
        }
        if(meal.getUserId() != null && meal.getUserId() == userId) {
            repository.put(meal.getId(), meal);
            return meal;
        }
        return null;
    }

    @Override
    public void delete(int userId, int mealId) {
        if(repository.get(mealId).getUserId() != null && repository.get(mealId).getUserId() == userId)
        repository.remove(mealId);
        else throw new NotFoundException("Еда отсутствует или принадлежит другому пользователю");
    }

    @Override
    public Meal get(int userId, int mealId) {
        if(repository.get(mealId).getUserId() != null && repository.get(mealId).getUserId() == userId)
        return repository.get(mealId);
        return null;
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
                .filter(meal -> DateTimeUtil.isBetween(repository.get(meal).getDate(), startDate, endDate))
                .collect(Collectors.toList());

    }

  }


package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Collection;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;


    @Override
    public Meal save(Meal meal, int userId) {
        Meal meal1 = repository.save(meal, userId);
        if (meal1 == null)
            throw new NotFoundException("Еда отсутствует или принадлежит другому пользователю");
        return meal1;
    }

    @Override
    public void delete(int userId, int mealId) {

        repository.delete(userId, mealId);


    }

    @Override
    public Meal get(int userId, int mealId) {
        Meal meal = repository.get(userId, mealId);
        if (meal == null)
            throw new NotFoundException("Еда отсутствует или принадлежит другому пользователю");

        return meal;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    @Override
    public void update(Meal meal, int userId) {
        try {
            repository.save(meal, userId);
        } catch (NullPointerException e) {
            throw new NotFoundException("Еда отсутствует или принадлежит другому пользователю");
        }
    }
    @Override
    public Collection<Meal> getFilterDate(LocalDate startDate, LocalDate endDate) {
       return repository.getFilterDate(startDate, endDate);
    }

}
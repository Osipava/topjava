package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;


@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<MealWithExceed> getAll() {
        log.info("getAll");
        return MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay());
    }

    public List<MealWithExceed> getFilterDate(LocalDate startDate, LocalDate endDate) {
        log.info("getFilterDate");
        return MealsUtil.getWithExceeded(service.getFilterDate(startDate, endDate), AuthorizedUser.getCaloriesPerDay());
    }

    public List<MealWithExceed> getFilterTime(LocalTime startTime, LocalTime endTime) {
        log.info("getFilterTime");
        return MealsUtil.getFilteredWithExceeded(service.getAll(AuthorizedUser.id()), startTime, endTime, AuthorizedUser.getCaloriesPerDay());
    }

    public Meal get(int mealId) {
        log.info("get {}", mealId);
        Meal meal = service.get(AuthorizedUser.id(), mealId);
        if (meal.getUserId() == null || meal.getUserId() != AuthorizedUser.id())
            throw new NotFoundException("Еда отсутствует или принадлежит другому пользователю");
        else
            return meal;
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        return service.save(meal, AuthorizedUser.id());
    }

    public void delete(int mealId) {
        log.info("delete {}", mealId);
        Meal meal = service.get(AuthorizedUser.id(), mealId);
        if (meal.getUserId() == null || meal.getUserId() != AuthorizedUser.id())
            throw new NotFoundException("Еда отсутствует или принадлежит другому пользователю");
        else
            service.delete(AuthorizedUser.id(), mealId);
    }

    public void update(Meal meal, int mealId) {
        log.info("update {} with id={}", meal, mealId);
        if (meal.getUserId() == null || meal.getUserId() != AuthorizedUser.id())
            throw new NotFoundException("Еда отсутствует или принадлежит другому пользователю");
        else {
            checkIdConsistent(meal, mealId);
            service.update(meal, AuthorizedUser.id());
        }
    }


}
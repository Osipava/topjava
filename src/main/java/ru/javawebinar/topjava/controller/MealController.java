package ru.javawebinar.topjava.controller;


import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoImpl;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class MealController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/meals.jsp";
    private static String LIST_MEALS = "/listMeal.jsp";
    private MealDao dao = new MealDaoImpl();



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.delete(id);
            request.setAttribute("meals", dao.getAllMealWithExceeds());
            response.sendRedirect(request.getContextPath() + "/index.jsp");

        } else if (action.equalsIgnoreCase("edit")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Meal meal = dao.getMealById(id);
            request.setAttribute("meal", meal);
            RequestDispatcher view = request.getRequestDispatcher(INSERT_OR_EDIT);
            view.forward(request, response);

        } else if (action.equalsIgnoreCase("listMeal")) {
            request.setAttribute("meals", dao.getAllMealWithExceeds());
            RequestDispatcher view = request.getRequestDispatcher(LIST_MEALS);
            view.forward(request, response);
        } else {
            RequestDispatcher view = request.getRequestDispatcher(INSERT_OR_EDIT);
            view.forward(request, response);
        }


    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("datetime")), request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));
        String id = request.getParameter("id");

        if (id == null || id.isEmpty()) {
            dao.create(meal);
        } else {
            meal.setId(Integer.parseInt(id));
            dao.edit(meal);
        }
        RequestDispatcher view = request.getRequestDispatcher(LIST_MEALS);
        request.setAttribute("meals", dao.getAllMealWithExceeds());
        view.forward(request, response);
    }
}

package ru.javawebinar.topjava.web;


import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class MealServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MealDao dao = new MealDaoImpl();
    private MealsUtil mealsUtil = new MealsUtil();

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.delete(id);
            request.setAttribute("meals",mealsUtil.allMealsWithExceeded(dao.getAll()));
            response.sendRedirect(request.getContextPath() + "/meals.jsp");

        } else if (action.equalsIgnoreCase("edit")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Meal meal = dao.getById(id);
            request.setAttribute("meal", meal);
            RequestDispatcher view = request.getRequestDispatcher("/meals.jsp");
            view.forward(request, response);

        } else if (action.equalsIgnoreCase("listMeal")) {
            request.setAttribute("meals", mealsUtil.allMealsWithExceeded(dao.getAll()));
            RequestDispatcher view = request.getRequestDispatcher("/listMeal.jsp");
            view.forward(request, response);
        } else {
            RequestDispatcher view = request.getRequestDispatcher("/meals.jsp");
            view.forward(request, response);
        }


    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Meal mea = new Meal(LocalDateTime.parse(request.getParameter("datetime")), request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));
        String id = request.getParameter("id");

        if (id == null || id.isEmpty()) {
            dao.create(mea);
        } else {
            mea.setId(Integer.parseInt(id));
            dao.edit(mea);
        }

        request.setAttribute("meals", mealsUtil.allMealsWithExceeded(dao.getAll()));
        RequestDispatcher view = request.getRequestDispatcher("/listMeal.jsp");
        view.forward(request, response);
    }
}

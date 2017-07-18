
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add new Meal</title>
</head>
<body>
<a href="index.jsp">Home</a>
</br>

<form method="POST" action='meals' name="createMeal">
    <table>
        <th></th>
        <th></th>
        <tr>
            <td><input type="hidden" readonly="readonly" name="id"
                       value="${meal.id}"/> <br/></td>
        </tr>
        <tr>
            <td>Дата и время :</td>
            <td><input type="datetime" name="datetime"
                       value="${meal.dateTime}"/> <br/></td>
        </tr>
        <tr>
            <td>Описание :</td>
            <td><input
                    type="text" name="description"
                    value="${meal.description}"/> <br/></td>
        </tr>
        <tr>
            <td>Калории :</td>
            <td><input
                    type="text" name="calories"
                    value="${meal.calories}"/> <br/></td>

        </tr>
        <tr>
            <td><input type="submit" value="Добавить"/></td>
        </tr>
    </table>
</form>
</body>
</html>
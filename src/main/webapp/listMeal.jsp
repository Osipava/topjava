<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://localhost:8080/topjava/functions" %>


<html>
<head>
    <title>Title</title>
</head>
<body>
<h1 align="center">MEALS</h1>
</br>

<table border="1" width="100%" cellpadding="5">

    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan=2>Action</th>
    </tr>

    <c:forEach var="meal" items="${meals}">
        <tr style="color: ${meal.exceed ? 'red' : 'green'}">
            <td>${f:formatLocalDateTime(meal.dateTime, 'dd.MM.yyyy')}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            <td><a href="meals?action=edit&id=${meal.id}">Edit</a></td>

        </tr>
    </c:forEach>
</table>

<p><a href="meals?action=insert">Add Meal</a></p>
</body>
</html>
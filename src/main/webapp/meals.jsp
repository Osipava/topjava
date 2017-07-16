<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://localhost:8080/topjava/functions" %>


<html>
<head>
    <title>Title</title>
</head>
<body>
<h1 align="center">MEALS</h1>

<table border="1" width="100%" cellpadding="5">

    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Exceed</th>
    </tr>

    <c:forEach var="meal" items="${list}">
        <tr style="color: <c:out value="${meal.isExceed()? 'red' : 'green'}" />">
            <td>${f:formatLocalDateTime(meal.dateTime, 'dd.MM.yyyy')}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>${meal.exceed}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

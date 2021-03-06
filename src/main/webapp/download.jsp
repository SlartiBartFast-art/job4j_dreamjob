<%--
  Created by IntelliJ IDEA.
  User: SlartiBartFast-art
  Date: 24.09.2021
  Time: 18:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Download</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
    <table class="table">
        <thead>
        <tr>
            <th>URL</th>
            <th>View</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${images}" var="image" varStatus="status">
            <tr valign="top">
                <td><a href="<c:url value='/download?name=${image}'/>">Download</a></td>
                <td>
                    <img src="<c:url value='/download?name=${image}'/>" width="100px" height="100px"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <h2>Download image</h2> <!-- В браузере она отобразится, как кнопка с выбором файла.
Давайте сделаем форму с вводом файла. -->
    <form action="<%=request.getContextPath()%>/photo?id=<%=request.getParameter("id")%>"
          method="post" enctype="multipart/form-data">
        <div class="checkbox">
            <input type="file" name="file"> <!-- В html есть форма загрузки файла. <input type="file" name="file"> -->
        </div>
        <button type="submit" class="btn btn-default">Submit</button>
    </form>
</div>

</body>
</html></html>

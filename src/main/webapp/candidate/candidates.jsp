<%--
 создадим отдельную страницу для кандидатов.
 Запустим приложение и откроем страницу http://localhost:8080/dreamjob/candidates.jsp
  Created by IntelliJ IDEA.
  User: SlartiBartFast-art
  Date: 22.09.2021
  Time: 14:32
  To change this template use File | Settings | File Templates.
  1. JSTL замена Scriplets [#2516]
  % for (Candidate candidate : (Collection<Candidate>) request.getAttribute("posts")) { %>
                    <tr>
                        <td> <!--Добавьте иконку в таблицу и ссылку на страницу edit.
                         Если перейти по ссылке, то загрузиться страница edit.jsp.-->
                            <a href="<%=request.getContextPath()%>/candidate/edit.jsp?id=<%=candidate.getId()%>">
                                <i class="fa fa-edit mr-3"></i>
                            </a>
                            <%= candidate.getName() %>
                        </td>
                    </tr>
                    <% } %>
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="ru.job4j.dream.store.PsqlStore" %>
<%@ page import="ru.job4j.dream.model.Candidate" %>
<%@ page import="java.util.Collection" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <!-- Добавьте библиотеку иконок.добавим возможность редактировать вакансию -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title>Работа мечты</title>
</head>
<body>
<div class="container pt-3">
    <div class="row">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link"
                   href="<%=request.getContextPath()%>/index.do">Главная</a>
            </li>
            <li class="nav-item">
                <a class="nav-link"
                   href="<%=request.getContextPath()%>/posts.do">Вакансии</a>
            </li>
            <li class="nav-item">
                <a class="nav-link"
                   href="<%=request.getContextPath()%>/post/edit.jsp">Добавить вакансию</a>
            </li>
            <li class="nav-item">
                <a class="nav-link"
                   href="<%=request.getContextPath()%>/candidates.do">Кандидаты</a>
            </li>
            <li class="nav-item">
                <a class="nav-link"
                   href="<%=request.getContextPath()%>/candidate/edit.jsp">Добавить кандидата</a>
            </li>
            <c:if test="${user == null}">
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/login/login.jsp">Войти</a>
                </li>
            </c:if>
            <c:if test="${user != null}">
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/logout.do">
                        <c:out value="${user.name}"/> | Выйти</a>
                </li>
            </c:if>
        </ul>
    </div>
</div>
<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                Кандидаты
            </div>
            <div class="card-body">
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col"></th>
                        <th scope="col">ID</th>
                        <th scope="col">Названия</th>
                        <th scope="col">Фото</th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tbody> <!-- c:forEach items="posts" var="post"
          Переменная posts была загружена в PostServlet.-->
                    <c:forEach items="${candidates}" var="candidate">
                        <tr>
                            <td><!--Добавьте иконку в таблицу и ссылку на страницу edit.
                         Если перейти по ссылке, то загрузиться страница edit.jsp.-->
                                <!-- элементов HTML <a href>.Этот элемент используется для перехода
                                с одной страницы и другую. Браузер отправляет запрос серверу отдать новую страницу. -->
                                <a href='<c:url value="/candidate/edit.jsp?id=${candidate.id}"/>'>
                                    <i class="fa fa-edit mr-3"></i>
                                </a><!-- c:out value="post.name"- Вывод значения post.  -->
                            </td>
                            <td><c:out value="${can.id}"/></td>
                            <td><c:out value="${can.name}"/></td>
                            <td>
                                <a href='<c:url value="/photo/upload.jsp?id=${can.id}"/>'>
                                    Добавить фото</a>
                            </td>
                            <td>
                                <a href='<c:url value="/photo/remove.jsp?id=${can.id}"/>'>
                                    Удалить кандидата и фото</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>

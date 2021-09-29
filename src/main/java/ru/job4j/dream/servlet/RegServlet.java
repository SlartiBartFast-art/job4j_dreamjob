package ru.job4j.dream.servlet;

import ru.job4j.dream.model.User;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 4. Регистрация пользователя. [#283110]
 * Уровень : 3. Мидл Категория : 3.2. Servlet JSP Топик : 3.2.6. Filter, Security
 * Создайте страница reg.jsp. Сервлет RegServlet.
 * Авторизация и регистрацию сделайте через Store.
 * В предыдущих уроках мы загрузали пользователя в коде.
 * User admin = new User();
 * admin.setName("Admin");
 * admin.setEmail(email);
 * sc.setAttribute("user", admin);
 * Нужно это переделать на Store.instOf().findByEmail().
 * @author SlartiBartFast-art
 * @version 02
 * @since 28.09.21
 */
public class RegServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        if (PsqlStore.instOf().findByEmail(email) == null) {
            User user = new User();
            user.setName(req.getParameter("name"));
            user.setEmail(email);
            user.setPassword(req.getParameter("password"));
            PsqlStore.instOf().save(user);
            req.setAttribute("error", "Регистрация завершена");
        } else {
            req.setAttribute("error", "Пользователь с таким email уже существует");
        }
        req.getRequestDispatcher("register/reg.jsp").forward(req, resp);
    }
}

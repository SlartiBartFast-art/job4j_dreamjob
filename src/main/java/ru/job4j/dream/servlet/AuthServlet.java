package ru.job4j.dream.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 0. Страница Login.jsp [#281230]
 * Уровень : 3. Мидл Категория : 3.2. Servlet JSPТопик : 3.2.6. Filter, Security
 * Сделаем страницу для входа в приложение под конкретным пользователем.
 * Это процесс называется авторизация.
 * login.jps + пропишем ссылку на нее в index.jps
 * Создадим сервлет авториСервлет проверяет почту и пароль, если они совпадают,
 * то переходит на страницу вакансий. Если нет, то возвращает обратно на страницу Login.
 * Пропишем его в web.xml.
 * @author SlartiBartFast-art
 * @version 01
 * @since 27.09.21
 */
public class AuthServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if ("root@local".equals(email) && "root".equals(password)) {
            resp.sendRedirect(req.getContextPath() + "/posts.do");
        } else {
            req.setAttribute("error", "Не верный email или пароль");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}

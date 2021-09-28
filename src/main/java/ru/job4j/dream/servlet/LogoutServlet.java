package ru.job4j.dream.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 1. HttpSession [#6869]00
 * Уровень : 3. МидлКатегория : 3.2. Servlet JSPТопик : 3.2.6. Filter, Security
 * Добавим сервлет LogoutServlet, который будет обрабатывать этот запрос
 * мы получаем сессию в следующей строке:
 * HttpSession session = req.getSession();
 * у этой сессии вызываем метод invalidate() который очищает сессию
 * и удаляет все добавленные ранее в нее атрибуты.
 * После этого мы будем перенаправлены на страницу авторизации следующей строкой:
 * req.getRequestDispatcher("login.jsp").forward(req, resp);
 * а также не забываем добавить наш сервлет в web.xml
 * @author SlartiBartFast-art
 * @version 01
 * @since 28.09.21
 */
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.invalidate();
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}

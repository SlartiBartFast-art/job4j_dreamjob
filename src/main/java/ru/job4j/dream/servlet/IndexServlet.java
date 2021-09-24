package ru.job4j.dream.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 0. MVC в Servlet. [#6868]
 * Уровень : 3. Мидл Категория : 3.2. Servlet JSP Топик : 3.2.4. Шаблон MVC
 * В сервлете переопределен метод doGet, а не doPost как было в сервлете PostServlet.
 * Когда в браузере открывается любая ссылка, он отправляет http запрос с типом GET.
 * Когда в браузере отправляется form нужно использовать метод doPost.
 * В самом теле метода, мы перенаправляем запрос в index.jsp.
 * req.getRequestDispatcher("index.jsp").forward(req, resp);
 *В request и response можно добавить информацию, для отображения на JSP.
 * @author SlartiBartFast-art
 * @version 01
 * @since 23.09.21
 */
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}

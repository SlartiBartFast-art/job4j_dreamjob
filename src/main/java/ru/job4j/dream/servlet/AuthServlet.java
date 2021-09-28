package ru.job4j.dream.servlet;

import ru.job4j.dream.model.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
 * 1. HttpSession [#6869]
 * Уровень : 3. МидлКатегория : 3.2. Servlet JSP Топик : 3.2.6. Filter, Security
 *  Когда браузер отправляет запрос в tomcat создается объект HttpSession.
 *  В объекте HttpSession можно хранить информацию о текущем пользователе.
 * Доработаем сервлет AuthServlet.
 * @author SlartiBartFast-art
 * @version 02
 * @since 27.09.21
 */
public class AuthServlet extends HttpServlet {
    /**
     * Если пользователь ввел верную почту и пароль, то мы записываем в HttpSession детали этого пользователя.     *
     * HttpSession sc = req.getSession();
     *             User admin = new User();
     *             admin.setName("Admin");
     *             admin.setEmail(email);
     *             sc.setAttribute("user", admin);
     * Теперь эти данные можно получить на другой странице.
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if ("root@local".equals(email) && "root".equals(password)) {
            HttpSession sc = req.getSession();
            User admin = new User();
            admin.setName("Admin");
            admin.setEmail(email);
            sc.setAttribute("user", admin);
            resp.sendRedirect(req.getContextPath() + "/posts.do");
        } else {
            req.setAttribute("error", "Не верный email или пароль");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}

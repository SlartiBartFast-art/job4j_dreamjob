package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.PsqlStore;
import ru.job4j.dream.store.Store;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 1. Servlet. Web.xml [#6866]02
 * Уровень : 3. Мидл Категория : 3.2. Servlet JSPТопик : 3.2.3. Servlet
 * Чтобы tomcat знал о нашем классе нужно его прописать в настройку окружения - web.xml
 * 3. Редактирование вакансии. [#277566]
 * Уровень : 3. Мидл Категория : 3.2. Servlet JSPТопик : 3.2.3. Servlet
 * Редактирование  posts.jsp.-- post/edit.jsp -- PostServlet
 * Внесение изминений в метод doPost(...) для возможности редактировать вакансию
 * Последний элемент редактирования - это загрузка id в сервлет.
 * 0. MVC в Servlet. [#6868]
 * * Уровень : 3. Мидл Категория : 3.2. Servlet JSP Топик : 3.2.4. Шаблон MVC
 * Мы уже сделали PostServlet. Доработаем его. Добавим метод doGet.
 * В методу doGet мы загружаем в request список вакансий.
 * req.setAttribute("posts", Store.instOf().findAllPosts());
 * Обратите внимание в методе doPost тоже изменен адрес.
 * resp.sendRedirect(req.getContextPath() + "/posts.do"); -- was posts.jsp
 * 1. HttpSession [#6869]
 * Уровень : 3. МидлКатегория : 3.2. Servlet JSP Топик : 3.2.6. Filter, Security
 *Напомню, что данные мы загружаем через Servlet, поэтому получим объект user из HttpSession.
 * @author SlartiBartFast-art
 * @version 05
 * @since 22.09.21
 */
public class PostServlet extends HttpServlet {

    /**
     * req.setAttribute("posts", new ArrayList<>(PsqlStore.instOf().findAllPosts()));
     *         req.setAttribute("user", req.getSession().getAttribute("user"));
     *         req.getRequestDispatcher("/post/posts.jsp").forward(req, resp);
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("posts", PsqlStore.instOf().findAllPosts());
        req.getRequestDispatcher("/post/posts.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        PsqlStore.instOf().save(
                new Post(
                        Integer.parseInt(req.getParameter("id")),
                        req.getParameter("name")
                )
        );
        resp.sendRedirect(req.getContextPath() + "/posts.do");
    }
}

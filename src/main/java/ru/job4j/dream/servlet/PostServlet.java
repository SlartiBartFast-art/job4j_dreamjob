package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.Store;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 1. Servlet. Web.xml [#6866]02
 * Уровень : 3. Мидл Категория : 3.2. Servlet JSPТопик : 3.2.3. Servlet
 * Чтобы tomcat знал о нашем классе нужно его прописать в настройку окружения - web.xml
 * 3. Редактирование вакансии. [#277566]
 * Уровень : 3. Мидл Категория : 3.2. Servlet JSPТопик : 3.2.3. Servlet
 * Редактирование  posts.jsp.-- post/edit.jsp -- PostServlet
 * Внесение изминений в метод doPost(...) для возможности редактировать вакансию
 * Последний элемент редактирования - это загрузка id в сервлет.
 *
 * @author SlartiBartFast-art
 * @version 02
 * @since 22.09.21
 */
public class PostServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Store.instOf().save(
                new Post(
                        Integer.parseInt(req.getParameter("id")),
                        req.getParameter("name")
                )
        );
        resp.sendRedirect(req.getContextPath() + "/posts.jsp");
    }
}

package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 2. CandidateSerlvet. Создание кандидата. [#2513]02
 * Уровень : 3. МидлКатегория : 3.2. Servlet JSPТопик : 3.2.3. Servlet
 * Создайте CandidateServlet.
 * Добавьте методы save(Candidate) в хранилище(Store).
 * Оживите страницу добавления кандитата.(web.xml)
 * 0. MVC в Servlet. [#6868]
 * Уровень : 3. Мидл Категория : 3.2. Servlet JSP Топик : 3.2.4. Шаблон MVC
 * Мы уже сделали CandidateServlet. Доработаем его. Добавим метод doGet.
 * В методу doGet мы загружаем в request список вакансий.
 * req.setAttribute("posts", Store.instOf().findAllPosts());
 * Обратите внимание в методе doPost тоже изменен адрес.
 * resp.sendRedirect(req.getContextPath() + "/candidates.do"); -- was candidates.jsp
 * 3. Редактирование вакансии. [#277566 #207261]02
 * Уровень : 3. Мидл Категория : 3.2. Servlet JSP Топик : 3.2.3. Servlet
 * В этом уроке мы добавим возможность редактировать вакансию.
 * Последний элемент - это загрузка id в сервлет.
 * Integer.valueOf(req.getParameter("id")),
 *
 * @author SlartiBartFast-art
 * @version 04
 * @since 21.09.21
 */
public class CandidateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("posts", Store.instOf().findAllCandidates());
        req.getRequestDispatcher("candidates.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Store.instOf().saveCandidate(
                new Candidate(
                        Integer.parseInt(req.getParameter("id")),
                        req.getParameter("name")
                )
        );
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}

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
 * @author SlartiBartFast-art
 * @version 01
 * @since 21.09.21
 */
public class CandidateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Store.instOf().saveCandidate(new Candidate(0, req.getParameter("name")));
        resp.sendRedirect(req.getContextPath() + "/candidates.jsp");
    }
}

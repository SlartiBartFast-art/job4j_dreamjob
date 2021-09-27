package ru.job4j.servlets;

import ru.job4j.dream.store.PsqlStore;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemoveServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("candidates", PsqlStore.instOf().findAllCandidates());
        request.getRequestDispatcher("candidates.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        PsqlStore.instOf().remove(Integer.parseInt(request.getParameter("id")));
        response.sendRedirect(request.getContextPath() + "/candidates.do");
        File file = new File("c:\\images\\" + request.getParameter("id") + ".jpg");
        file.delete();
    }
}

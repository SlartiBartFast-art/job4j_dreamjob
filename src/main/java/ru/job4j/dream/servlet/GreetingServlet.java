package ru.job4j.dream.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.servlets.Email;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 4. Ajax Jquery [#58534]00
 * Уровень : 3. МидлКатегория : 3.2. Servlet JSPТопик : 3.2.8. JS, JQuery, Ajax
 * создадим сервлет, который будет отрабатывать запросы.
 * + его нужно прописать в web.xml
 * <servlet>
 * <servlet-name>GreetingServlet</servlet-name>
 * <servlet-class>ru.job4j.dream.servlet.GreetingServlet</servlet-class>
 * </servlet>
 * <servlet-mapping>
 * <servlet-name>GreetingServlet</servlet-name>
 * <url-pattern>/greet</url-pattern>
 * </servlet-mapping>
 * Если запустить Tomcat и перейти по адресу
 * http://localhost:8080/dreamjob/greet?name=Petr
 * получим рабочую форму обработки запроса, где
 * greet - имя сервлета в web.xml
 * ?name=Petr - параметры запроса.
 */
public class GreetingServlet extends HttpServlet {
    private final static Gson GSON = new GsonBuilder().create();
    private final List<Email> emails = new CopyOnWriteArrayList<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        Email email = GSON.fromJson(req.getReader(), Email.class);
        emails.add(email);
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        String json = GSON.toJson(email);
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        String json = GSON.toJson(emails);
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }
}

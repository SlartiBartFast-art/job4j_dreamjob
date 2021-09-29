package ru.job4j.dream.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 2. Filter [#2517]
 * Уровень : 3. Мидл Категория : 3.2. Servlet JSPТопик : 3.2.6. Filter, Security
 * Интерфейс Filter содержит метод doFilter. Через этот метод будут проходить запросы к сервлетам.
 * Если запрос идет на сервлет авторизации, то проверку делать не будем.
 * if (uri.endsWith("auth.do")) {
 * chain.doFilter(sreq, sresp);
 * return;
 * }
 * Для всех остальных запросов мы проверяем текущего пользователя.
 * Если его нет, то отправляем на страницу авторизации. *
 * if (req.getSession().getAttribute("user") == null) {
 * resp.sendRedirect(req.getContextPath() + "/login.jsp");
 * return;
 * }
 * public interface FilterChain {
 * void doFilter(javax.servlet.ServletRequest servletRequest, javax.servlet.ServletResponse servletResponse){}}
 * Сервлетный фильтр
 * Сервлетный фильтр, в соответствии со спецификацией, это Java-код, пригодный для повторного
 * использования и позволяющий преобразовать содержание HTTP-запросов, HTTP-ответов и информацию,
 * содержащуюся в заголовках HTML. Сервлетный фильтр занимается предварительной обработкой запроса,
 * прежде чем тот попадает в сервлет, и/или последующей обработкой ответа, исходящего из сервлета.
 * FilterChain: FilterChain - это упорядоченный набор независимых фильтров.
 * Вызывает вызов следующего фильтра в цепочке или, если вызывающий фильтр является последним
 * фильтром в цепочке, вызывает вызов ресурса в конце цепочки.
 * Пропишем его в web.xml
 * 4. Регистрация пользователя. [#283110]
 * Уровень : 3. Мидл Категория : 3.2. Servlet JSP Топик : 3.2.6. Filter, Security
 * В AuthFilter добавьте игнорировние сервлета reg.do.
 *
 * @author SlartiBartFast-art
 * @version 02
 * @since 28.09.21
 */
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest sreq, ServletResponse sresp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) sreq;
        HttpServletResponse resp = (HttpServletResponse) sresp;
        String uri = req.getRequestURI();
        if (uri.endsWith("auth.do") || uri.endsWith("reg.do")) {
            chain.doFilter(sreq, sresp);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        chain.doFilter(sreq, sresp);
    }
}

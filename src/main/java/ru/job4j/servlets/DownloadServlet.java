package ru.job4j.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 1. Загрузка и скачивание файла. [#154183]
 * Уровень : 3. Мидл Категория : 3.2. Servlet JSPТопик : 3.2.5.1. Form
 * Задача этого сервлета отдать файл, который лежит на сервере.
 * Чтобы указать, что сервер отправляет файл нужно становить тип данных. *
 * resp.setContentType("application/octet-stream");
 * resp.setHeader("Content-Disposition", "attachment; filename=\"" + users.getName() + "\"");
 * Перезапустите сервлет и откройте ссылку /download
 * Теперь мы видим, что браузер скачивает файл. *
 */
public class DownloadServlet extends HttpServlet {
    /*
1. Мы выставляем заголовок ответа в протоколе.
Таким образом мы сообщаем браузеру, что будем отправлять файл.
String name = req.getParameter("name");
2. Открываем поток и записываем его в выходной поток servlet.
resp.getOutputStream().write(in.readAllBytes());
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        File downloadFile = null;
        for (File file : new File("C:\\images").listFiles()) {
            if (name.equals(file.getName())) {
                downloadFile = file;
                break;
            }
        }
        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + downloadFile.getName() + "\"");
        try (FileInputStream stream = new FileInputStream(downloadFile)) {
            resp.getOutputStream().write(stream.readAllBytes());
        }
    }
}

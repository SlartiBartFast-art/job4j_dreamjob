package ru.job4j.servlets;

/**
 * 4.1. JSON формат. [#238554]
 * Уровень : 3. МидлКатегория : 3.2. Servlet JSPТопик : 3.2.8. JS, JQuery, Ajax
 * модель Email, которую мы будем сериализовать/десериализовать:
 * @author SlariBartFast-art
 * @version 02
 * @since 01.10.21
 */
public class Email {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


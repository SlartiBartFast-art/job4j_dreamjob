package ru.job4j.dream.store;

import ru.job4j.dream.model.Post;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 2. Scriplet. Динамическая таблица. [#276520]
 * Уровень : 3. Мидл Категория : 3.2.
 * Servlet JSPТопик : 3.2.2. JSP
 * path 2.
 * @author SlartiBartFast-art
 * @since 21.09.21
 */
public class Store {
    private static final Store INST = new Store();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private Store() {
        posts.put(1, new Post(1, "Junior Java Job"));
        posts.put(2, new Post(2, "Middle Java Job"));
        posts.put(3, new Post(3, "Senior Java Job"));
    }

    public static Store instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }
}

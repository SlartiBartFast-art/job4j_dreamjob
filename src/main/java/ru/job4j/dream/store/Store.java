package ru.job4j.dream.store;

import com.sun.jdi.PathSearchingVirtualMachine;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 2. Scriplet. Динамическая таблица. [#276520]
 * Уровень : 3. Мидл Категория : 3.2.
 * Servlet JSPТопик : 3.2.2. JSP
 * path 2. Хранилище
 * 1. Servlet. Web.xml [#6866]
 * Уровень : 3. МидлКатегория : 3.2. Servlet JSPТопик : 3.2.3. Servlet
 * Добавим в хранилище метод save(Post post). *
 * Добавим ключ для генерации ID.
 *
 * @author SlartiBartFast-art
 * @version 02
 * @since 21.09.21
 */
public class Store {
    private static final Store INST = new Store();

    private static AtomicInteger postId = new AtomicInteger(4);
    private static AtomicInteger candidateId = new AtomicInteger(4);

    private Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private Store() {
        posts.put(1, new Post(1, "Junior Java Job"));
        posts.put(2, new Post(2, "Middle Java Job"));
        posts.put(3, new Post(3, "Senior Java Job"));
        candidates.put(1, new Candidate(1, "Junior Java"));
        candidates.put(2, new Candidate(2, "Middle Java"));
        candidates.put(3, new Candidate(3, "Senior Java"));
    }

    public static Store instOf() {
        return INST;
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    public void save(Post post) {
        post.setId(postId.incrementAndGet());
        posts.put(post.getId(), post);
    }

    public void saveCandidate(Candidate candidate) {
        candidate.setId(candidateId.incrementAndGet());
        candidates.put(candidate.getId(), candidate);
    }

    public static void main(String[] args) {
        Store store = new Store();
       var store1 = Store.instOf();
       store1.saveCandidate(new Candidate(0, "Petr Arsentev"));
       for (Candidate candidate : store1.findAllCandidates()) {
           System.out.println(candidate.toString());
       }
    }
}

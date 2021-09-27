package ru.job4j.dream.store;

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
 * ранее class Store преименован 250921 согласно задания
 * @author SlartiBartFast-art
 * @version 03
 * @since 21.09.21
 */
public class MemStore {
    private static final MemStore INST = new MemStore();

    private static AtomicInteger postId = new AtomicInteger(4);
    private static AtomicInteger candidateId = new AtomicInteger(4);

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private MemStore() {
        posts.put(1, new Post(1, "Junior Java Job"));
        posts.put(2, new Post(2, "Middle Java Job"));
        posts.put(3, new Post(3, "Senior Java Job"));
        candidates.put(1, new Candidate(1, "Junior Java"));
        candidates.put(2, new Candidate(2, "Middle Java"));
        candidates.put(3, new Candidate(3, "Senior Java"));
    }

    public static MemStore instOf() {
        return INST;
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    /**
     * Если id вакансии равен 0, то нужно сгенерировать новую id.
     *
     * @param post Object
     */
    public void save(Post post) {
        if (post.getId() == 0) {
            post.setId(postId.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    /**
     * Метод для поиска вакансии по id.
     *
     * @param id Object Post
     * @return Object Post or null if there was no mapping for key
     */
    public Post findById(int id) {
        return posts.get(id);
    }

    /**
     * Если id candidate равен 0, то нужно сгенерировать новую id.
     * @param candidate Object
     */
    public void saveCandidate(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(candidateId.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
    }

    /**
     * Метод для поиска вакансии по id.
     *
     * @param id Object Post
     * @return Object Post or null if there was no mapping for key
     */
    public Candidate findByIdCandidate(int id) {
        return candidates.get(id);
    }

    public static void main(String[] args) {
        MemStore store = new MemStore();
        var store1 = MemStore.instOf();
        store1.saveCandidate(new Candidate(1, "Petr Arsentev"));
        store1.saveCandidate(new Candidate(2, "Petr Arsentev"));
        store1.saveCandidate(new Candidate(3, "Petr Arsentev"));
        store1.saveCandidate(new Candidate(1, "Иван Иванов"));
        for (Candidate candidate : store1.findAllCandidates()) {
            System.out.println(candidate.toString());
        }
    }
}

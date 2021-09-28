package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.util.Collection;
import java.util.Optional;

/**
 * 1. PsqlStore. [#281226]
 * Уровень : 3. Мидл Категория : 3.2. Servlet JSPТопик : 3.2.5. База данных в Web
 * Сделаем интерфейс Store
 * @author SlartiBartFast-art
 * @version 01
 * @since 25.09.21
 */
public interface Store {
    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    void save(Post post);

    void saveCandidate(Candidate candidate);

    Post findById(int id);

    Candidate findByIdCandidate(int id);

    void remove(int id);

}

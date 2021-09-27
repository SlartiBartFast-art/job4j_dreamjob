package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

/**
 * 1. PsqlStore. [#281226]
 * Уровень : 3. Мидл Категория : 3.2. Servlet JSPТопик : 3.2.5. База данных в Web
 * Сделаем класс для экспериментов с PsqlStore. *
 *
 * @author SlartiBartFast-art
 * @version 01
 * @since 24.09.21
 */
public class PsqlMain {

    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        store.save(new Post(0, "Java Job"));
        for (Post post : store.findAllPosts()) {
            System.out.println("1____ " + post.getId() + " " + post.getName());
        }

        Post post = store.findById(1);
        System.out.println("2____ "  + post.getId() +  " " + post.getName());

        Candidate candidate = new Candidate(0, "Petr Arsentev");
        store.saveCandidate(candidate);

        int id = 0;
        for (Candidate candidate1 : store.findAllCandidates()) {
            System.out.println("3____ " + candidate1.getId() + " " + candidate1.getName());
            id = candidate1.getId();
        }

        Candidate candidate1 = store.findByIdCandidate(id);
        System.out.println("4____ " + candidate1.getId() + " " + candidate1.getName());
    }
}

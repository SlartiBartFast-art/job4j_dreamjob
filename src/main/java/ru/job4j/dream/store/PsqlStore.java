package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * 1. PsqlStore. [#281226]
 * Уровень : 3. Мидл Категория : 3.2. Servlet JSPТопик : 3.2.5. База данных в Web
 * сделаем хранилище для работы с базой postgresql.
 * Создадим класс описывающий интерфейс хранилища.
 * class BasicDataSource простой org.apache.commons.dbcp.BasicDataSource объект,
 * то есть базовую реализацию javax.sql.DataSource, которая настраивается через
 * свойства JavaBeans. Короче говоря, чтобы создать простой BasicDataSource объект, вам необходимо:
 * Создайте BasicDataSource объект и настройте базу данных. Используйте setDriverClassName
 * (String driverClassName)метод для установки имени класса драйвера jdbc. Используйте setUrl
 * (String url)метод для установки URL-адреса.
 *
 * @author SlartiBartFast-art
 * @version 02
 * @since 24.09.21
 */
public class PsqlStore implements Store {
    final static Logger LOGGER =
            LogManager.getLogger(PsqlStore.class.getName());
    private final BasicDataSource pool = new BasicDataSource();

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("db.properties")
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    /**
     * Find all Post Object in table(PostgreSQL) post
     *
     * @return all present Post Object in table post view Collection<Post>
     */
    @Override
    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"), it.getString("name")));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("findAllPosts() ERROR. Unable to SQL query", e);
        }
        return posts;
    }

    /**
     * Find all Candidate Object in table(PostgreSQL) candidate
     *
     * @return all present Candidate in table candidate view Collection<Candidate>
     */
    @Override
    public Collection<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidate")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(it.getInt("id"), it.getString("name")));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("findAllCandidates() ERROR. Unable to SQL query", e);
        }
        return candidates;
    }

    /**
     * Saves the Post Object in the table post (PostgreSQL),
     * or updates the table post if it present
     *
     * @param post Object Post
     */
    @Override
    public void save(Post post) {
        if (post.getId() == 0) {
            create(post);
        } else {
            update(post);
        }
    }

    /**
     * Saves the Candidate Object in the table candidate (PostgreSQL),
     * or updates the table post if it present
     *
     * @param candidate Object Candidate
     */
    @Override
    public void saveCandidate(Candidate candidate) {
        if (candidate.getId() == 0) {
            createCandidate(candidate);
        } else {
            updateCandidate(candidate);
        }
    }

    @Override
    public void remove(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "DELETE FROM candidate name WHERE id = (?)")
        ) {
            ps.setInt(2, id);
            ps.execute();
        } catch (SQLException e) {
            LOGGER.error("remove(int id) ERROR. Unable to SQL query", e);
        }
    }

    @Override
    public void save(User user) {
        if (user.getId() == 0) {
            createUser(user);
        } else {
            updateUser(user);
        }
    }

    private User createUser(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO users(name, email, password) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("createUser(User user) ERROR. Unable to SQL query", e);
        }
        return user;
    }

    private void updateUser(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "UPDATE users SET name = (?), email = (?), password = (?) WHERE id = (?)")
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getName());
            ps.setString(3, user.getName());
            ps.setInt(4, user.getId());
            ps.execute();
        } catch (SQLException e) {
            LOGGER.error("updateUser(User user) ERROR. Unable to SQL query", e);
        }
    }

    @Override
    public User findByEmail(String email) {
        User user = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM users WHERE email = (?)")
        ) {
            ps.setString(1, email);
            try (ResultSet it = ps.executeQuery()) {
                user = new User(
                        it.getInt("id"), it.getString("name"),
                        it.getString("email"), it.getString("password")
                );
            }
        } catch (SQLException e) {
            LOGGER.error("findByEmail(String email) ERROR. Unable to SQL query", e);
        }
        return user;
    }

    /**
     * Создание вакансии. Здесь выполняется обычный sql запрос.
     *
     * @param post Object
     * @return Post Object
     */
    private Post create(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO post(name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("create(Post post) ERROR. Unable to SQL query", e);
        }
        return post;
    }

    /**
     * Create new Object Candidate in the table candidate
     *
     * @param candidate Object
     * @return Candidate from table candidate
     */
    private Candidate createCandidate(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO candidate(name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("createCandidate(Candidate candidate) ERROR. Unable to SQL query", e);
        }
        return candidate;
    }

    /**
     * Does update value write Post Object in the table post
     *
     * @param post Object
     */
    private void update(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "UPDATE post SET name = (?) WHERE id = (?)")
        ) {
            ps.setString(1, post.getName());
            ps.setInt(2, post.getId());
            ps.execute();
        } catch (SQLException e) {
            LOGGER.error("update(Post post) ERROR. Unable to SQL query", e);
        }
    }

    /**
     * Does update value write Candidate Object in the table candidate
     *
     * @param candidate Object
     */
    private void updateCandidate(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "UPDATE candidate SET name = (?) WHERE id = (?)")
        ) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getId());
            ps.execute();
        } catch (SQLException e) {
            LOGGER.error("updateCandidate(Candidate candidate) ERROR. Unable to SQL query", e);
        }
    }

    /**
     * Implements find Object Post in table (PostgresSQL) from value id
     * Replace Post post = new Post(0, "") by Optional.of(new Post(0, ""));
     * or  Optional<Post> post = Optional.empty();
     * Replace e.printStackTrace(); by LOGGER.fatal("Unable to SQL query.", e);
     *
     * @param id value Post object
     * @return Post Object
     */
    @Override
    public Post findById(int id) {
        Post post = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT name FROM post WHERE id = (?)")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                post = new Post(id, it.getString("name"));

            }
        } catch (SQLException e) {
            LOGGER.error("findById(int id) ERROR. Unable to SQL query", e);
        }
        return post;
    }

    /**
     * Implements find Object Post in table (PostgresSQL) from value id
     * Replace  Candidate candidate = new Candidate(0, ""); by
     * Optional<Candidate> candidate = Optional.empty();
     * Replace e.printStackTrace(); by LOGGER
     *
     * @param id value Candidate object
     * @return Candidate Object
     */
    @Override
    public Candidate findByIdCandidate(int id) {
       Candidate candidate = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT name FROM candidate WHERE id = (?)")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                candidate = new Candidate(id, it.getString("name"));
            }
        } catch (SQLException e) {
            LOGGER.error("findByIdCandidate(int id) ERROR. Unable to SQL query", e);
        }
        return candidate;
    }
}

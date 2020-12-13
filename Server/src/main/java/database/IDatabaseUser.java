package database;

import model.User;

import java.util.List;

public interface IDatabaseUser {
    void add(User user);

    Boolean isLoginExist(String login);

    User findByLoginAndPassword(String login, String password);

    void remove(String login);

    List<User> findAll();
}

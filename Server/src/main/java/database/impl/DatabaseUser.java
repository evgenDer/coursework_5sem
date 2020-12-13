package database.impl;

import database.IDatabaseUser;
import database.query.DatabaseQuery;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUser implements IDatabaseUser {
    private static DatabaseUser instance;
    private final DatabaseConnection dbConnection;

    private DatabaseUser() {
        dbConnection = DatabaseConnection.getInstance();
    }

    public static synchronized DatabaseUser getInstance() {
        if (instance == null) {
            instance = new DatabaseUser();
        }
        return instance;
    }

    @Override
    public void add(User user) {
        try {
            PreparedStatement userStatement = dbConnection.getConnect().prepareStatement(DatabaseQuery.ADD_USER);
            userStatement.setString(1, user.getLogin());
            userStatement.setString(2, user.getPassword());
            userStatement.setBoolean(3, user.getAdmin());
            userStatement.setString(4, user.getName());
            userStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while adding user to database");
            e.printStackTrace();
        }
    }

    @Override
    public Boolean isLoginExist(String login) {
        String receivedLogin = "";
        try {
            PreparedStatement statement = dbConnection.getConnect().prepareStatement(DatabaseQuery.FIND_USER_BY_LOGIN);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                receivedLogin = resultSet.getString(1);
            }
        } catch (SQLException e) {
            System.out.println("Error while find user by login");
            e.printStackTrace();
        }
        return login.equals(receivedLogin);
    }

    public User findByLoginAndPassword(String login, String password) {
        try {
            User user = new User();
            PreparedStatement statement = dbConnection.getConnect().
                    prepareStatement(DatabaseQuery.FIND_USER_BY_LOGIN_AND_PASSWORD);
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user.setLogin(resultSet.getString(1));
                user.setAdmin(resultSet.getBoolean(2));
                user.setName(resultSet.getString(3));
                return user;
            }
        } catch (SQLException e) {
            System.out.println("Error while find user by login");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(String login) {
        try {
            PreparedStatement statement = dbConnection.getConnect().prepareStatement(DatabaseQuery.REMOVE_USER);
            statement.setString(1, login);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while find user by login");
            e.printStackTrace();
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try {
            PreparedStatement statement = dbConnection.getConnect().prepareStatement(DatabaseQuery.FIND_ALL_USERS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setLogin(resultSet.getString(1));
                user.setAdmin(resultSet.getBoolean(2));
                user.setName(resultSet.getString(3));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}

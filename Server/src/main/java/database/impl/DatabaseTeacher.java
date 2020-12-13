package database.impl;

import database.IDatabaseTeacher;
import database.query.DatabaseQuery;
import model.Teacher;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseTeacher implements IDatabaseTeacher  {
    private static DatabaseTeacher instance;
    private final DatabaseConnection dbConnection;

    private DatabaseTeacher() {
        dbConnection = DatabaseConnection.getInstance();
    }

    public static synchronized DatabaseTeacher getInstance() {
        if (instance == null) {
            instance = new DatabaseTeacher();
        }
        return instance;
    }

    @Override
    public void add(Teacher teacher) {
        try {
            PreparedStatement statement = dbConnection.getConnect().prepareStatement(DatabaseQuery.ADD_TEACHER);
            statement.setString(1, teacher.getUser().getLogin());
            statement.setString(2, teacher.getUniversity());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while adding teacher to database");
            e.printStackTrace();
        }
    }

    @Override
    public List<Teacher> findAll() {
        List<Teacher> teachers = new ArrayList<>();

        try {
            PreparedStatement statement = dbConnection.getConnect().prepareStatement(DatabaseQuery.FIND_TEACHERS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Teacher teacher = new Teacher();
                teacher.getUser().setLogin(resultSet.getString(1));
                teacher.getUser().setName(resultSet.getString(2));
                teacher.setUniversity(resultSet.getString(3));
                teacher.setIdTeacher(resultSet.getInt(4));
                teachers.add(teacher);
            }
            return teachers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }


}

package database.impl;

import database.IDatabaseTest;
import database.query.DatabaseQuery;
import model.Student;
import model.Test;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class DatabaseTest implements IDatabaseTest {
    private static DatabaseTest instance;
    private final DatabaseConnection dbConnection;

    private DatabaseTest() {
        dbConnection = DatabaseConnection.getInstance();
    }

    public static synchronized DatabaseTest getInstance() {
        if (instance == null) {
            instance = new DatabaseTest();
        }
        return instance;
    }

    @Override
    public void add(Test test, int idStudent) {
        try {
            PreparedStatement statement = dbConnection.getConnect()
                    .prepareStatement(DatabaseQuery.ADD_RESULT_TEST);
            statement.setInt(1, idStudent);
            statement.setInt(2, test.getResultTest());
            statement.setDate(3, Date.valueOf(test.getDate()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Student findAll(int idStudent) {
        Student student = new Student();
        student.setIdStudent(idStudent);
        try {
            ArrayList<Test> testsStudent = new ArrayList<>();
            PreparedStatement statement = dbConnection.getConnect()
                    .prepareStatement(DatabaseQuery.FIND_PROGRESS);
            statement.setInt(1, idStudent);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Test test = new Test();
                test.setIdTest(resultSet.getInt(1));
                test.setDate(LocalDate.parse(resultSet.getDate(2).toString()));
                test.setResultTest(resultSet.getInt(3));
                testsStudent.add(test);
            }
            student.setListTests(testsStudent);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public int findAverageScore(int idStudent) {
        try {
            PreparedStatement statement = dbConnection.getConnect()
                    .prepareStatement(DatabaseQuery.FIND_AVERAGE_SCORE);
            statement.setInt(1, idStudent);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}

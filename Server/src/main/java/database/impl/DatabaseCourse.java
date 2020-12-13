package database.impl;

import database.IDatabaseCourse;
import database.query.DatabaseQuery;
import model.Course;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseCourse implements IDatabaseCourse {
    private final DatabaseConnection dbConnection;
    private static DatabaseCourse instance;

    private DatabaseCourse() {
        dbConnection = DatabaseConnection.getInstance();
    }

    public static synchronized DatabaseCourse getInstance() {
        if (instance == null) {
            instance = new DatabaseCourse();
        }
        return instance;
    }

    @Override
    public List<Course> findAll() {
        List<Course> coursesList = new ArrayList<>();

        try {
            PreparedStatement statement = dbConnection.getConnect().prepareStatement(DatabaseQuery.FIND_ALL_COURSES);
            return getCoursesList(coursesList, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coursesList;
    }

    @Override
    public void add(Course course) {
        try {
            PreparedStatement userStatement = dbConnection.getConnect().prepareStatement(DatabaseQuery.ADD_COURSE);
            userStatement.setString(1, course.getName());
            userStatement.setString(2, course.getLevel());
            userStatement.setDouble(3, course.getCost());
            userStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while adding user to database");
            e.printStackTrace();
        }
    }

    @Override
    public List<Course> findByLevel(String level) {
        List<Course> coursesList = new ArrayList<>();

        try {
            PreparedStatement statement = dbConnection.getConnect().prepareStatement(DatabaseQuery.FIND_COURSES_BY_LEVEl);
            statement.setObject(1, level);

            return getCoursesList(coursesList, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coursesList;
    }

    private List<Course> getCoursesList(List<Course> coursesList, PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Course course = new Course();
            course.setIdCourse(resultSet.getInt(1));
            course.setName(resultSet.getString(2));
            course.setLevel(resultSet.getString(3));
            course.setCost(resultSet.getDouble(4));

            coursesList.add(course);
        }
        return coursesList;
    }

    @Override
    public Boolean hasCorrectId(int idCourse) {
        int id = -1;
        try {
            PreparedStatement statement = dbConnection.getConnect().prepareStatement(DatabaseQuery.FIND_COURSE_BY_ID);
            statement.setInt(1, idCourse);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id == idCourse;
    }

    @Override
    public void updateByCost(double cost, int idCourse) {
        try {
            PreparedStatement statement = dbConnection.getConnect().prepareStatement(DatabaseQuery.UPDATE_COURSE_COST);
            statement.setDouble(1, cost);
            statement.setInt(2, idCourse);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateByName(String name, int idCourse) {
        try {
            PreparedStatement statement = dbConnection.getConnect().prepareStatement(DatabaseQuery.UPDATE_COURSE_NAME);
            statement.setString(1, name);
            statement.setInt(2, idCourse);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

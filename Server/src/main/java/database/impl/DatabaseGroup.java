package database.impl;

import database.IDatabaseGroup;
import database.query.DatabaseQuery;
import model.Group;
import model.Teacher;
import util.Transformer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseGroup implements IDatabaseGroup {
    private static DatabaseGroup instance;
    private final DatabaseConnection dbConnection;

    private DatabaseGroup() {
        dbConnection = DatabaseConnection.getInstance();
    }

    public static synchronized DatabaseGroup getInstance() {
        if (instance == null) {
            instance = new DatabaseGroup();
        }
        return instance;
    }

    @Override
    public void updateByTeacherId(int idTeacher, int idGroup) {
        try {
            PreparedStatement statement = dbConnection.getConnect()
                    .prepareStatement(DatabaseQuery.UPDATE_GROUP_BY_TEACHER);
            statement.setInt(1, idTeacher);
            statement.setInt(2, idGroup);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateByCourseId(int idCourse, int idGroup) {
        try {
            PreparedStatement statement = dbConnection.getConnect()
                    .prepareStatement(DatabaseQuery.UPDATE_GROUP_BY_COURSE);
            statement.setInt(1, idCourse);
            statement.setInt(2, idGroup);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(Group group) {
        try {
            PreparedStatement statement = dbConnection.getConnect().prepareStatement(DatabaseQuery.ADD_GROUP);
            statement.setInt(1, group.getCourse().getIdCourse());
            statement.setInt(2, group.getTeacher().getIdTeacher());
            statement.setTime(3, group.getStartLesson());
            statement.setString(4, group.getClassDays());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while adding user to database");
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int idGroup) {
        try {
            PreparedStatement statement = dbConnection.getConnect().prepareStatement(DatabaseQuery.REMOVE_GROUP);
            statement.setInt(1, idGroup);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Group> findAll() {
        List<Group> groups = new ArrayList<>();
        try {
            PreparedStatement statement = dbConnection.getConnect().prepareStatement(DatabaseQuery.FIND_GROUPS);
            groups = getAllGroups(statement);
            return groups;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }

    @Override
    public List<Group> findAllGroupTeacher(String login) {
        List<Group> groups = new ArrayList<>();
        try {
            PreparedStatement statement = dbConnection.getConnect()
                    .prepareStatement(DatabaseQuery.FIND_GROUPS_BY_TEACHER);
            statement.setString(1, login);
            groups = getAllGroups(statement);
            return groups;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }

    @Override
    public Teacher findGroupByWeekdays(String login, int dayCode) {
        Teacher teacher = new Teacher();
        try {
            PreparedStatement statement = dbConnection.getConnect()
                    .prepareStatement(DatabaseQuery.FIND_GROUPS_BY_WEEKDAYS);
            statement.setString(1, login);
            statement.setInt(2, dayCode);
            teacher.setListGroups(getAllGroups(statement));
            return teacher;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacher;
    }

    private List<Group> getAllGroups(PreparedStatement statement) throws SQLException {
        List<Group> groups = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Group group = new Group();
            group.setIdGroup(resultSet.getInt(1));
            group.setStartLesson(resultSet.getTime(2));
            group.setClassDays(Transformer.getDaysOfWeekString(resultSet.getString(3)));
            group.getCourse().setName(resultSet.getString(4));
            group.setLevel(resultSet.getString(5));
            group.getTeacher().getUser().setName(resultSet.getString(6));
            groups.add(group);
        }
        return groups;
    }
}

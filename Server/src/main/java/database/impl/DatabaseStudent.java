package database.impl;

import database.IDatabaseStudent;
import database.query.DatabaseQuery;
import model.Discount;
import model.Payment;
import model.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseStudent implements IDatabaseStudent {
    private static DatabaseStudent instance;
    private final DatabaseConnection dbConnection;

    private DatabaseStudent() {
        dbConnection = DatabaseConnection.getInstance();
    }

    public static synchronized DatabaseStudent getInstance() {
        if (instance == null) {
            instance = new DatabaseStudent();
        }
        return instance;
    }

    @Override
    public void updateByGroupId(int idGroup, int idStudent) {
        try {
            PreparedStatement statement = dbConnection.getConnect()
                    .prepareStatement(DatabaseQuery.UPDATE_GROUP_STUDENT);
            statement.setInt(1, idGroup);
            statement.setInt(2, idStudent);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateByLevel(String level, int idStudent) {
        try {
            PreparedStatement statement = dbConnection.getConnect()
                    .prepareStatement(DatabaseQuery.UPDATE_LEVEL_STUDENT);
            statement.setString(1, level);
            statement.setInt(2, idStudent);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDiscount(int discount, int idStudent) {
        try {
            PreparedStatement statement = dbConnection.getConnect()
                    .prepareStatement(DatabaseQuery.UPDATE_DISCOUNT);
            statement.setInt(1, discount);
            statement.setInt(2, idStudent);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePayment(int idStudent) {
        try {
            PreparedStatement statement = dbConnection.getConnect()
                    .prepareStatement(DatabaseQuery.UPDATE_PAYMENT);
            statement.setInt(1, idStudent);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAttendant(int countAttendant, int idStudent) {
        try {
            PreparedStatement statement = dbConnection.getConnect()
                    .prepareStatement(DatabaseQuery.UPDATE_ATTENDANT);
            statement.setInt(1, countAttendant);
            statement.setInt(2, idStudent);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(Student student) {
        try {
            PreparedStatement statement = dbConnection.getConnect().prepareStatement(DatabaseQuery.ADD_STUDENT);
            statement.setInt(1, student.getGroup().getIdGroup());
            statement.setString(2, student.getName());
            statement.setString(3, student.getSurname());
            statement.setString(4, student.getEmail());
            statement.setString(5, student.getLevel());
            statement.setDate(6, student.getDateStart());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while adding user to database");
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int idStudent) {
        try {
            PreparedStatement statement = dbConnection.getConnect().prepareStatement(DatabaseQuery.REMOVE_STUDENT);
            statement.setInt(1, idStudent);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        try {
            PreparedStatement statement = dbConnection.getConnect().prepareStatement(DatabaseQuery.FIND_STUDENTS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setIdStudent(resultSet.getInt(1));
                student.getGroup().setIdGroup(resultSet.getInt(2));
                student.setName(resultSet.getString(3));
                student.setSurname(resultSet.getString(4));
                student.setEmail(resultSet.getString(5));
                student.setLevel(resultSet.getString(6));
                student.setDateStart(resultSet.getDate(7));
                students.add(student);
            }
            return students;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public List<Student> findDebtors() {
        List<Student> debtors = new ArrayList<>();
        try {
            PreparedStatement statement = dbConnection.getConnect().prepareStatement(DatabaseQuery.FIND_DEBTORS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setIdStudent(resultSet.getInt(1));
                student.setName(resultSet.getString(2));
                student.setSurname(resultSet.getString(3));
                student.setEmail(resultSet.getString(4));
                student.getGroup().setIdGroup(resultSet.getInt(5));
                debtors.add(student);
            }
            return debtors;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return debtors;
    }

    @Override
    public Payment findPayment(int idStudent) {
        try {
            PreparedStatement statement = dbConnection.getConnect().prepareStatement(DatabaseQuery.FIND_PAYMENT);
            statement.setInt(1, idStudent);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Payment payment = new Payment();
                Discount discount = new Discount(resultSet.getInt(1));
                payment.setDiscount(discount);
                payment.setCost(resultSet.getDouble(2));
                return payment;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Student> findAttendances(int idClass) {
        List<Student> students = new ArrayList<>();
        try {
            PreparedStatement statement = dbConnection.getConnect()
                    .prepareStatement(DatabaseQuery.FIND_STUDENTS_ATTENDANCE);
            statement.setInt(1, idClass);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setIdStudent(resultSet.getInt(1));
                student.setName(resultSet.getString(2));
                student.setSurname(resultSet.getString(3));
                student.setCountAttendant(resultSet.getInt(4));
                students.add(student);
            }
            return students;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}

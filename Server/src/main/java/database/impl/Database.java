package database.impl;

public class Database  extends AbstractFactory {
    @Override
    public DatabaseUser getUsers() {
        return DatabaseUser.getInstance();
    }

    @Override
    public DatabaseCourse getCourses() {
        return DatabaseCourse.getInstance();
    }

    @Override
    public DatabaseTeacher getTeachers() {
        return DatabaseTeacher.getInstance();
    }

    @Override
    public DatabaseGroup getGroups() {
        return DatabaseGroup.getInstance();
    }

    @Override
    public DatabaseStudent getStudents() {
        return DatabaseStudent.getInstance();
    }

    @Override
    public DatabaseTest getTest() {
        return DatabaseTest.getInstance();
    }

}

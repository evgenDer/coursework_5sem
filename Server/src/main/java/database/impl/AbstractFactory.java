package database.impl;

public abstract class AbstractFactory {
    public abstract DatabaseCourse getCourses();

    public abstract DatabaseUser getUsers();

    public abstract DatabaseTeacher getTeachers();

    public abstract DatabaseGroup getGroups();

    public abstract DatabaseStudent getStudents();

    public abstract DatabaseTest getTest();
}
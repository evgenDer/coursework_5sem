package database;

import model.Teacher;

import java.util.List;

public interface IDatabaseTeacher {
    void add(Teacher teacher);

    List<Teacher> findAll();
}

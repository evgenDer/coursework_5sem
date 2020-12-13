package database;

import model.Course;

import java.util.List;

public interface IDatabaseCourse {
    List<Course> findAll();

    void add(Course course);

    List<Course> findByLevel(String level);

    Boolean hasCorrectId(int idCourse);

    void updateByName(String name, int idCourse);

    void updateByCost(double cost, int idCourse);
}

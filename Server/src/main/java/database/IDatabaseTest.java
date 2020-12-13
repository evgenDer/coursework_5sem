package database;

import model.Student;
import model.Test;

public interface IDatabaseTest {
    void add(Test test, int idStudent);

    Student findAll(int idStudent);

    int findAverageScore(int idStudent);
}

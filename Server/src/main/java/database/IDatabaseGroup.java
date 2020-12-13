package database;

import model.Group;
import model.Teacher;

import java.util.List;

public interface IDatabaseGroup {
    void updateByTeacherId(int idTeacher, int idGroup);

    void updateByCourseId(int idCourse, int idGroup);

    void add(Group group);

    void remove(int idGroup);

    List<Group> findAll();

    List<Group> findAllGroupTeacher(String login);

    Teacher findGroupByWeekdays(String login, int dayCode);
}

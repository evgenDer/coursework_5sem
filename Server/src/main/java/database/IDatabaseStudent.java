package database;

import model.Payment;
import model.Student;

import java.util.List;

public interface IDatabaseStudent {
    void updateByGroupId(int idGroup, int idStudent);

    void updateByLevel(String level, int idStudent);

    void updateDiscount(int discount, int idStudent);

    void updatePayment(int idStudent);

    void updateAttendant(int countAttendant, int idStudent);

    void add(Student student);

    void remove(int idStudent);

    List<Student> findAll();

    List<Student> findDebtors();

    Payment findPayment(int idStudent);

    List<Student> findAttendances(int idClass);
}

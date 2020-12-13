package model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class Student implements Serializable {
    private int idStudent;
    private Group group = new Group();
    private Discount discount;
    private String name;
    private String surname;
    private String email;
    private String level;
    private Date dateStart;
    private List<Test> listTests;
    private int countAttendant;
    private int averageScore;

    public int getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(int averageScore) {
        this.averageScore = averageScore;
    }

    public int getCountAttendant() {
        return countAttendant;
    }

    public void setCountAttendant(int countAttendant) {
        this.countAttendant = countAttendant;
    }

    public List<Test> getListTests() {
        return listTests;
    }

    public void setListTests(List<Test> listTests) {
        this.listTests = listTests;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }
}

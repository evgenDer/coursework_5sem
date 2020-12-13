package model;

import java.io.Serializable;

public class Course implements Serializable {
    private int idCourse;
    private String name;
    private String level;
    private double cost;


    public void setIdCourse(int idCourse) {
        this.idCourse = idCourse;
    }

    public int getIdCourse() {
        return idCourse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}

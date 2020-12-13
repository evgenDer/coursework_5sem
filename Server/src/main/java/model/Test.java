package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Test implements Serializable {
    private int idTest;
    private LocalDate date = LocalDate.now();
    private int resultTest;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getIdTest() {
        return idTest;
    }

    public void setIdTest(int idTest) {
        this.idTest = idTest;
    }

    public int getResultTest() {
        return resultTest;
    }

    public void setResultTest(int resultTest) {
        this.resultTest = resultTest;
    }
}

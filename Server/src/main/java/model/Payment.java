package model;

import java.io.Serializable;

public class Payment implements Serializable {
    private double cost;
    private Discount discount;

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
}

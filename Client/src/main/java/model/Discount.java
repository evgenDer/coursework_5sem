package model;

import java.io.Serializable;

public class Discount implements Serializable {
    private Boolean isFamily;
    private Boolean isFriends;
    private Boolean isDuration;
    private int additional;
    private double value;


    public Discount(int value) {
        this.value = value;
    }

    public double getValueDiscount() {
        return value;
    }

    public void setValueDiscount(double value) {
        this.value = value;
    }

    public Discount() {
        this.isFamily = false;
        this.isFriends = false;
        this.isDuration = false;
        this.additional = 0;
    }

    public Boolean getDiscountFamily() {
        return isFamily;
    }

    public void setDiscountFamily(Boolean discountFamily) {
        isFamily = discountFamily;
    }

    public Boolean getDiscountFriends() {
        return isFriends;
    }

    public void setDiscountFriends(Boolean discountFriends) {
        isFriends = discountFriends;
    }

    public Boolean getDiscountDuration() {
        return isDuration;
    }

    public void setDiscountDuration(Boolean discountDuration) {
        isDuration = discountDuration;
    }

    public int getAdditionalDiscount() {
        return additional;
    }

    public void setAdditionalDiscount(int additional) {
        this.additional = additional;
    }

}

package util;

import model.Discount;

public class Calculation {
    private final static int FACTOR_FAMILY = 5;
    private final static int FACTOR_FRIENDS = 3;
    private final static int FACTOR_DURATION = 7;

    static public int calculateDiscount(Discount discount) {
        int resultDiscount = 0;
        if (discount.getDiscountDuration()) {
            resultDiscount += FACTOR_DURATION;
        }
        if (discount.getDiscountFamily()) {
            resultDiscount += FACTOR_FAMILY;
        }
        if (discount.getDiscountFriends()) {
            resultDiscount += FACTOR_FRIENDS;
        }
        resultDiscount += discount.getAdditionalDiscount();
        return resultDiscount;
    }


}

package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Transformer {
    public static String getDayOfWeekFromDayCode(char code) {
        switch (code) {
            case '0':
                return "Понедельник";
            case '1':
                return "Вторник";
            case '2':
                return "Среда";
            case '3':
                return "Четверг";
            case '4':
                return "Пятница";
            case '5':
                return "Суббота";
            case '6':
                return "Воскресенье";
        }
        return null;
    }

    static public String getStringFromArrayList(List list, String splitter) {
        AtomicReference<String> resultString = new AtomicReference<>("");
        list.forEach(el -> resultString.set(resultString + (el.toString() + splitter)));
        return resultString.get();
    }

    static public List getDaysOfWeekFromDaysCode(String codeOfDays) {
        List list = new ArrayList();
        for (int i = 0; i < codeOfDays.length(); i++) {
            list.add(getDayOfWeekFromDayCode(codeOfDays.charAt(i)));
        }
        return list;
    }

    static public String getDaysOfWeekString(String codeOfDays) {
        return getStringFromArrayList(getDaysOfWeekFromDaysCode(codeOfDays),  ", ");
    }

}

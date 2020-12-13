package validation;

import components.AlertBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Validation {
    static final String REGEX_PASSWORD = "(?=.*[0-8.0.202-ea])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
    static final String REGEX_LOGIN = "[A-za-z0-9]{6,}+";
    static final String REGEX_DOUBLE = "\\d+([.,]?\\d*)";
    static final String REGEX_TIME = "^([0-1]\\d|2[0-3])(:[0-5]\\d)$";
    static final String REGEX_EMAIL = "^[^@]+@[^@.]+\\.[^@]+$";
    static final String REGEX_NAME = "^([А-Я]{1}[а-яё]{1,}|[A-Z]{1}[a-z]{1,})+$";

    static public Boolean hasCorrectDouble(TextField field) {
        if (!field.getText().matches(REGEX_DOUBLE)) {
            AlertBox alert = new AlertBox("error", "Данное поле должно содержать числа с плавающей точкой!");
            alert.show();
            return false;
        }
        return true;
    }

    static public Boolean hasCorrectTime(TextField field) {
        if (!field.getText().matches(REGEX_TIME)) {
            AlertBox alert = new AlertBox("error", "Неверный формат для времени! Верный формат hh:MM ");
            alert.show();
            return false;
        }
        return true;
    }

    static public Boolean hasCorrectEmail(TextField field) {
        if (!field.getText().matches(REGEX_EMAIL)) {
            AlertBox alert = new AlertBox("error", "Введен некорректный адрес электронной почты!");
            alert.show();
            return false;
        }
        return true;
    }

    static public Boolean hasCorrectName(TextField field) {
        if (!field.getText().matches(REGEX_NAME)) {
            return false;
        }
        return true;
    }

    static public Boolean hasPasswordCoincidence(PasswordField password, PasswordField passwordRepeat) {
        if (!passwordRepeat.getText().equals(password.getText())) {
            AlertBox alert = new AlertBox("error", "Пароли не совпадают!");
            alert.show();
            return false;
        }
        return true;
    }

    public static Boolean hasCorrectLogin(TextField fieldLogin) {
        if (!fieldLogin.getText().matches(REGEX_LOGIN)) {
            AlertBox alert = new AlertBox("error", "Логин должен быть не менее 6 " +
                    "символов и должен содержать латинские буквы или цифры!");
            alert.show();
            return false;
        }
        return true;
    }

    public static Boolean hasCorrectPassword(PasswordField fieldPassword) {
        if (!fieldPassword.getText().matches(REGEX_PASSWORD)) {
            AlertBox alert = new AlertBox("error", "Пароль должен содержать не менее 8 символов," +
                    " как минимум одну прописную букву," +
                    " одну заглавную букву, одну цифру и один спецсимвол из +-_@$!%*?&#.,;:[]{}\n");
            alert.show();
            return false;
        }
        return true;
    }


    static public Boolean hasEmptyFields(TextField... fields) {
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getText().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    static public Boolean hasEmptyFields(PasswordField... fields) {
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getText().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    static public Boolean hasEmptyFields(TextArea... fields) {
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getText().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    static public Boolean hasEmptyFields(ComboBox... boxes) {
        for (int i = 0; i < boxes.length; i++) {
            if (boxes[i].getSelectionModel().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}

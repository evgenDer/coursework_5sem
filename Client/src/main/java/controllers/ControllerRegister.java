package controllers;

import client.Client;
import components.AlertBox;
import components.Scene;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.User;
import validation.Validation;

public class ControllerRegister extends Controller {

    @FXML
    private TextField fieldRegLogin;

    @FXML
    private Button btnReg;

    @FXML
    private PasswordField fieldRegPassword;

    @FXML
    private TextField fieldRegName;

    @FXML
    private PasswordField fieldRegRepeatPassword;

    @FXML
    private PasswordField fieldSecretKey;

    @FXML
    private Button btnRegReturn;

    public final static String ADMIN_KEY = "123";

    @FXML
    void initialize() {
        btnRegReturn.setOnAction(event -> {
            btnRegReturn.getScene().getWindow().hide();
            new Scene("Авторизация", "fxml/auth.fxml");
        });

        btnReg.setOnAction(event -> {
            if (validateFields()) {
                Client clientHandler = Client.getInstance();
                User user = new User();
                user.setPassword(fieldRegPassword.getText());
                user.setLogin(fieldRegLogin.getText());
                user.setName(fieldRegName.getText());
                if (ADMIN_KEY.equals(fieldSecretKey.getText()))  {
                    user.setAdmin(true);
                }
                clientHandler.sendMessage("addUser");
                clientHandler.sendObject(user);
                Boolean isCorrectAddition = (Boolean) clientHandler.readObject();
                AlertBox alert;
                if (isCorrectAddition) {
                    btnReg.getScene().getWindow().hide();
                    alert = new AlertBox("success", "Вы успешно зарегистрировались!");
                    clientHandler.setLogin(fieldRegLogin.getText());
                    super.login(user.getAdmin());
                } else {
                    alert = new AlertBox("error", "Пользователь с таким логином уже существует");
                }
                alert.show();
            }
        });

    }

    private boolean validateFields() {
        if (Validation.hasEmptyFields(fieldRegName, fieldRegLogin)
                || Validation.hasEmptyFields(fieldRegRepeatPassword, fieldRegPassword)) {
            AlertBox alert = new AlertBox("warning", "Пожалуйста, заполните все поля");
            alert.show();
            return false;
        }

        if (!Validation.hasCorrectLogin(fieldRegLogin)) {
            return false;
        }

        if (!Validation.hasPasswordCoincidence(fieldRegPassword, fieldRegRepeatPassword)) {
            return false;
        }

        if (!Validation.hasCorrectPassword(fieldRegPassword)) {
            return false;
        }

        return true;
    }


}
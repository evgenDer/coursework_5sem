package controllers;


import client.Client;
import components.AlertBox;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import components.Scene;
import model.User;
import validation.Validation;


public class ControllerAuth extends Controller{

    @FXML
    private TextField fieldAuthLogin;

    @FXML
    private Button btnAuth;

    @FXML
    private PasswordField fieldAuthPassword;

    @FXML
    private Button btnAuthRegister;

    @FXML
    void initialize() {
        Client client = Client.getInstance();
        btnAuthRegister.setOnAction(event -> {
            btnAuthRegister.getScene().getWindow().hide();
            new Scene("Регистрация", "fxml/register.fxml");
        });

        btnAuth.setOnAction(event -> {
            if (!Validation.hasEmptyFields(fieldAuthLogin) && !Validation.hasEmptyFields(fieldAuthPassword)) {
                client.sendMessage("checkLoginAndPassword");
                client.sendMessage(fieldAuthLogin.getText());
                client.sendMessage(fieldAuthPassword.getText());
                Boolean isCorrectLoginAndPassword = (Boolean) client.readObject();
                if (isCorrectLoginAndPassword) {
                    btnAuth.getScene().getWindow().hide();
                    Boolean isAdmin = (Boolean) client.readObject();
                    client.setLogin(fieldAuthLogin.getText());
                    super.login(isAdmin);
                } else {
                    AlertBox alert = new AlertBox("error", "Неверный логин или пароль");
                    alert.show();
                }
            } else {
                AlertBox alert = new AlertBox("warning", "Пожалуйста, заполните все поля");
                alert.show();
            }

        });
    }
}

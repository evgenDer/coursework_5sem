package controllers.admin;

import client.Client;
import components.AlertBox;
import components.Scene;
import controllers.Controller;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import model.User;


public class ControllerUsers extends Controller {

    @FXML
    private TableView<User> tableUsers;

    @FXML
    private TableColumn<?, ?> columnName;

    @FXML
    private TableColumn<?, ?> columnLogin;

    @FXML
    private TableColumn<User, Boolean> columnAdmin;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnRegister;

    @FXML
    void initialize() {
        super.init();
        Client clientHandler = Client.getInstance();
        ObservableList<User> list = FXCollections.observableArrayList();
        clientHandler.sendMessage("findAllUsers");

        int sizeList = (Integer) clientHandler.readObject();

        for (int i = 0; i < sizeList; i++) {
            User user = (User) clientHandler.readObject();
            list.add(user);
        }

        columnName.setCellValueFactory(new PropertyValueFactory("name"));
        columnLogin.setCellValueFactory(new PropertyValueFactory("login"));
        columnAdmin.setCellValueFactory(param -> {
            User person = param.getValue();
            SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(person.getAdmin());
            booleanProp.addListener((observable, oldValue, newValue) -> person.setAdmin(newValue));
            return booleanProp;
        });

        columnAdmin.setCellFactory(p -> {
            CheckBoxTableCell<User, Boolean> cell = new CheckBoxTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        tableUsers.setItems(list);

        btnRegister.setOnAction(event -> {
            btnRegister.getScene().getWindow().hide();
            new Scene("Регистрация", "fxml/register.fxml");
        });

        btnDelete.setOnAction(event -> {
            try {
                User selectedUser = tableUsers.getSelectionModel().getSelectedItem();
                clientHandler.sendMessage("removeUser");
                clientHandler.sendObject(selectedUser.getLogin());
                list.remove(selectedUser);
                AlertBox alert = new AlertBox("success", "Пользователь успешно удален!");
                alert.show();
            } catch (NullPointerException ex) {
                AlertBox alert = new AlertBox("warning", "Пожалуйста, выберите пользователя для удаления");
                alert.show();
            }
        });
    }
}



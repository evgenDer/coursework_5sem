package controllers.admin;

import client.Client;
import components.AlertBox;
import controllers.Controller;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Teacher;
import validation.Validation;

public class ControllerTeachers extends Controller {
    @FXML
    private Button btnAddTeacher;

    @FXML
    private TextField fieldTeacherLogin;

    @FXML
    private TextField fieldTeacherEducation;

    @FXML
    private Tab tabShowTeachers;

    @FXML
    private TableView<Teacher> tableShowTeachers;

    @FXML
    private TableColumn<Teacher, ?> columnId;

    @FXML
    private TableColumn<Teacher, ?> columnName;

    @FXML
    private TableColumn<Teacher, ?> columnLogin;

    @FXML
    private TableColumn<Teacher, ?> columnUniversity;

    Client clientHandler = Client.getInstance();

    @FXML
    void initialize() {
        super.init();

        btnAddTeacher.setOnAction(event -> addTeachers());

        tabShowTeachers.setOnSelectionChanged(event ->
                fillDataTeachers(tableShowTeachers, columnId, columnName, columnLogin, columnUniversity));
    }

    public void addTeachers() {
        if (!Validation.hasEmptyFields(fieldTeacherEducation, fieldTeacherLogin)) {
            Teacher teacher = new Teacher();
            teacher.getUser().setLogin(fieldTeacherLogin.getText());
            teacher.setUniversity(fieldTeacherEducation.getText());
            clientHandler.sendMessage("addTeacher");
            clientHandler.sendObject(teacher);
            Boolean isCorrectAddition = (Boolean) clientHandler.readObject();
            fieldTeacherLogin.setText("");
            fieldTeacherEducation.setText("");
            AlertBox alert;
            if (isCorrectAddition) {
                alert = new AlertBox("success", "Вы успешно добавили пользователя!");
            } else {
                alert = new AlertBox("error", "Пользователь с таким логином не существует!" +
                        "Сначала зарегистрируйтесь в системе!");
            }
            alert.show();
        } else {
            AlertBox alert = new AlertBox("warning", "Пожалуйста, заполните все поля");
            alert.show();
        }
    }


    public void fillDataTeachers(TableView table, TableColumn<Teacher, ?> ...columns) {
        ObservableList<Teacher> listTeachers = FXCollections.observableArrayList();
        clientHandler.sendMessage("findAllTeachers");

        int sizeList = (Integer) clientHandler.readObject();

        for (int i = 0; i < sizeList; i++) {
            Teacher teacher = (Teacher) clientHandler.readObject();
            listTeachers.add(teacher);
        }

        columns[0].setCellValueFactory(new PropertyValueFactory("idTeacher"));
        columns[1].setCellValueFactory(new PropertyValueFactory("name"));
        columns[2].setCellValueFactory(data-> new SimpleObjectProperty(data.getValue().getUser().getLogin()));
        columns[3].setCellValueFactory(new PropertyValueFactory("university"));

        table.setItems(listTeachers);
    }

}

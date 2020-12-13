package controllers.user;

import client.Client;
import components.AlertBox;
import controllers.Controller;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Student;
import model.Test;
import validation.Validation;

public class ControllerTest extends Controller {
    @FXML
    private TextField fieldCorrectAnswer;

    @FXML
    private TextField fieldErrorAnswer;

    @FXML
    private TextField fieldResult;

    @FXML
    private TextField field;

    @FXML
    private Button btnCalculate;

    @FXML
    private Button btnStudentId;

    private Client clientHandler = Client.getInstance();

    @FXML
    void initialize() {
        initUser();

        fieldCorrectAnswer.textProperty().addListener((observable, oldValue, newValue) -> {
            Validation.hasCorrectDouble(fieldCorrectAnswer);
        });

        fieldErrorAnswer.textProperty().addListener((observable, oldValue, newValue) -> {
            Validation.hasCorrectDouble(fieldErrorAnswer);
        });

        btnCalculate.setOnAction(event -> {
            if (!Validation.hasEmptyFields(fieldCorrectAnswer, fieldErrorAnswer)) {
                clientHandler.sendMessage("calculateResultTest");
                clientHandler.sendObject(fieldCorrectAnswer.getText());
                clientHandler.sendObject(fieldErrorAnswer.getText());
                fieldResult.setText(clientHandler.readObject().toString().replace(".0", ""));
            } else {
                AlertBox alert = new AlertBox("warning",
                        "Пожалуйста, заполните количество правильных и неправильных ответов");
                alert.show();
            }
        });
    }

    @FXML
    public void showTableStudents() {
        TableView<Student> tableStudents = new TableView<>();
        tableStudents.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Student, ?> columnIdStudent = new TableColumn<>("Номер студента");
        TableColumn<Student, ?> columnSurname = new TableColumn<>("Фамилия");
        TableColumn<Student, ?> columnName = new TableColumn<>("Имя");
        TableColumn<Student, ?> columnLevel = new TableColumn<>("Уровень");
        TableColumn<Student, ?> columnIdGroup = new TableColumn<>("Номер группы");

        tableStudents.getColumns().addAll(columnIdStudent, columnSurname,
                columnName, columnLevel, columnIdGroup);

        ObservableList<Student> listStudents = FXCollections.observableArrayList();
        clientHandler.sendMessage("findAllStudents");

        int sizeList = (Integer) clientHandler.readObject();
        for (int i = 0; i < sizeList; i++) {
            Student student = (Student) clientHandler.readObject();
            listStudents.add(student);
        }

        columnIdStudent.setCellValueFactory(new PropertyValueFactory("idStudent"));
        columnSurname.setCellValueFactory(new PropertyValueFactory("surname"));
        columnName.setCellValueFactory(new PropertyValueFactory("name"));
        columnLevel.setCellValueFactory(new PropertyValueFactory("level"));
        columnIdGroup.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getGroup().getIdGroup()));

        tableStudents.setItems(listStudents);

        if (!Validation.hasEmptyFields(fieldResult)) {
            super.showTable(tableStudents, "Таблица студентов");
            super.getTableStage().setOnCloseRequest(event -> {
                if (tableStudents.getItems().size() > 0 && tableStudents.getSelectionModel().getSelectedIndex() != -1) {
                    clientHandler.sendMessage("addResultTest");
                    Test test = new Test();
                    test.setResultTest(Integer.parseInt(fieldResult.getText()));
                    clientHandler.sendObject(tableStudents.getSelectionModel()
                            .getSelectedItem().getIdStudent());
                    clientHandler.sendObject(test);
                    AlertBox alert = new AlertBox("success", "Оценка за тест была добавлена!");
                    alert.show();
                } else {
                    AlertBox alert = new AlertBox("warning", "Добавьте студентов!");
                    alert.show();
                }
            });
        } else {
            AlertBox alert = new AlertBox("warning", "Подсчитайте результат теста!");
            alert.show();
        }
    }


}



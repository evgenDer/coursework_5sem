package controllers.admin;

import client.Client;
import components.AlertBox;
import controllers.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Course;
import validation.Validation;

public class ControllerCourses extends Controller {
    @FXML
    private Button btnAddCourse;

    @FXML
    private TextField fieldAddCourseName;

    @FXML
    private TextField fieldAddCourseCost;

    @FXML
    private ComboBox<String> boxAddCourseLevel;

    @FXML
    private TextField fieldCourseIdEdit;

    @FXML
    private TextField fieldEditCourseCost;

    @FXML
    private TextField fieldEditCourseName;

    @FXML
    private Button btnEditCourseCost;

    @FXML
    private Button btnEditCourseName;

    @FXML
    private Tab tabShowCourses;

    @FXML
    private TableView<Course> tableShowCourse;


    @FXML
    private Button btnEditSelected;

    @FXML
    private TableColumn<?, ?> columnIdCourse;

    @FXML
    private TableColumn<?, ?> columnName;

    @FXML
    private TableColumn<?, ?> columnCost;

    @FXML
    private TableColumn<?, ?> columnLevel;

    @FXML
    private Button btnSearchByLevel;

    @FXML
    private ComboBox<String> boxSearchCourseLevel;

    private final Client clientHandler = Client.getInstance();


    @FXML
    void initialize() {
        super.init();

        boxSearchCourseLevel.setItems(LANGUAGE_LEVELS);
        boxAddCourseLevel.setItems(LANGUAGE_LEVELS);

        tabShowCourses.setOnSelectionChanged(event -> fillDataCourses(tableShowCourse, columnName, columnIdCourse, columnCost, columnLevel));

        btnAddCourse.setOnAction(event -> addCourse());

        btnSearchByLevel.setOnAction(event -> searchByLevel());

        btnEditSelected.setOnAction(event -> fieldCourseIdEdit.setText(String.valueOf(tableShowCourse.getSelectionModel()
                .getSelectedItem().getIdCourse())));

        btnEditCourseName.setOnAction(event -> editCourseName());

        btnEditCourseCost.setOnAction(event -> editCourseCost());
    }

    public void editCourseName() {
        if (!Validation.hasEmptyFields(fieldEditCourseName, fieldCourseIdEdit)) {
            clientHandler.sendMessage("hasCorrectId");
            clientHandler.sendObject(Integer.parseInt(fieldCourseIdEdit.getText()));
            Boolean isCorrectId = (Boolean) clientHandler.readObject();
            if (isCorrectId) {
                clientHandler.sendMessage("updateCourseName");
                clientHandler.sendObject(Integer.parseInt(fieldCourseIdEdit.getText()));
                clientHandler.sendObject(fieldEditCourseName.getText());
                fieldEditCourseName.setText("");
                AlertBox alert = new AlertBox("success", "Название курса успешно отредактировано");
                alert.show();
            }
        } else {
            AlertBox alert = new AlertBox("warning", "Пожалуйста, заполните все поля");
            alert.show();
        }
    }

    public void editCourseCost() {
        if (!Validation.hasEmptyFields(fieldEditCourseCost, fieldCourseIdEdit)) {
            if (Validation.hasCorrectDouble(fieldEditCourseCost)) {
                clientHandler.sendMessage("hasCorrectId");
                clientHandler.sendObject(Integer.parseInt(fieldCourseIdEdit.getText()));
                Boolean isCorrectId = (Boolean) clientHandler.readObject();
                if (isCorrectId) {
                    String cost = fieldEditCourseCost.getText();
                    clientHandler.sendMessage("updateCourseCost");
                    clientHandler.sendObject(Integer.parseInt(fieldCourseIdEdit.getText()));
                    clientHandler.sendObject(Double.parseDouble((cost.contains(",")
                            ? cost.replace(',', '.') : cost)));
                    fieldEditCourseCost.setText("");
                    AlertBox alert = new AlertBox("success", "Цена курса успешно отредактирована");
                    alert.show();
                }
            }
        } else {
            AlertBox alert = new AlertBox("warning", "Пожалуйста, заполните все поля");
            alert.show();
        }
    }

    public void addCourse() {
        if (!Validation.hasEmptyFields(boxAddCourseLevel)
                && !Validation.hasEmptyFields(fieldAddCourseCost, fieldAddCourseName)) {
            if (Validation.hasCorrectDouble(fieldAddCourseCost)) {
                String cost = fieldAddCourseCost.getText();
                Course course = new Course();
                course.setCost(Double.parseDouble((cost.contains(",") ? cost.replace(',', '.') : cost)));
                course.setName(fieldAddCourseName.getText());
                course.setLevel(boxAddCourseLevel.getValue());
                AlertBox alert = new AlertBox("success", "Курс успешно добавлен");
                alert.show();
                fieldAddCourseCost.setText("");
                fieldAddCourseName.setText("");
                clientHandler.sendMessage("addCourse");
                clientHandler.sendObject(course);
            }
        } else {
            AlertBox alert = new AlertBox("warning", "Пожалуйста, заполните все поля");
            alert.show();
        }
    }

    public void fillDataCourses(TableView table, TableColumn ...columns) {
        ObservableList<Course> listCourses = FXCollections.observableArrayList();
        clientHandler.sendMessage("findAllCourses");

        int sizeList = (Integer) clientHandler.readObject();

        for (int i = 0; i < sizeList; i++) {
            Course course = (Course) clientHandler.readObject();
            listCourses.add(course);
        }

        columns[0].setCellValueFactory(new PropertyValueFactory("name"));
        columns[1].setCellValueFactory(new PropertyValueFactory("idCourse"));
        columns[2].setCellValueFactory(new PropertyValueFactory("level"));
        columns[3].setCellValueFactory(new PropertyValueFactory("cost"));

        table.setItems(listCourses);
    }

    public void searchByLevel() {
        ObservableList<Course> listCourses = FXCollections.observableArrayList();
        if (!Validation.hasEmptyFields(boxSearchCourseLevel)) {
            clientHandler.sendMessage("findCoursesByLevel");
            clientHandler.sendObject(boxSearchCourseLevel.getValue());

            int sizeList = (Integer) clientHandler.readObject();

            for (int i = 0; i < sizeList; i++) {
                Course course = (Course) clientHandler.readObject();
                listCourses.add(course);
            }
            tableShowCourse.setItems(listCourses);

        } else {
            AlertBox alert = new AlertBox("warning", "Веберите уровень для поиска");
            alert.show();
        }
    }

}

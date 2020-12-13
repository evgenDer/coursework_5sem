package controllers;

import components.Scene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller {
    protected final ObservableList<String> LANGUAGE_LEVELS = FXCollections.observableArrayList(
            "A1", "A2", "B1", "B2", "C1", "C2");

    protected final ObservableList<String> WEEKDAYS
            = FXCollections.observableArrayList("Понедельник", "Вторник", "Среда",
            "Четверг", "Пятница", "Суббота", "Воскресенье");

    @FXML
    private Button btnDiscount;

    @FXML
    private Button btnPayment;

    @FXML
    private Button btnStudents;

    @FXML
    private Button btnTeachers;

    @FXML
    private Button btnUsers;

    @FXML
    private Button btnGroups;

    @FXML
    private Button btnCourse;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnSchedule;

    @FXML
    private Button btnTests;

    @FXML
    private Button btnProgress;

    @FXML
    private Button btnAttendance;


    Stage tableStage;

    @FXML
    public void initUser() {
        btnGroups.setOnAction(event -> {
            btnGroups.getScene().getWindow().hide();
            new Scene("Группы", "fxml/user/groupsTeacherPage.fxml");
        });

        btnSchedule.setOnAction(event -> {
            btnSchedule.getScene().getWindow().hide();
            new Scene("Расписание", "fxml/user/schedulePage.fxml");
        });

        btnProgress.setOnAction(event -> {
            btnProgress.getScene().getWindow().hide();
            new Scene("Успеваемость", "fxml/user/progressPage.fxml");
        });

        btnTests.setOnAction(event-> {
            btnTests.getScene().getWindow().hide();
            new Scene("Тестирование", "fxml/user/testPage.fxml");
        });
        
        btnExit.setOnAction(event -> {
            btnExit.getScene().getWindow().hide();
            new Scene("Авторизация", "fxml/auth.fxml");
        });

        btnAttendance.setOnAction(event-> {
            btnAttendance.getScene().getWindow().hide();
            new Scene("Посещения", "fxml/user/attendancePage.fxml");
        });
    }

    @FXML
    public void init() {
        btnExit.setOnAction(event -> {
            btnExit.getScene().getWindow().hide();
            new Scene("Авторизация", "fxml/auth.fxml");
        });
        
        btnDiscount.setOnAction(event -> {
            btnDiscount.getScene().getWindow().hide();
            new Scene("Скидки", "fxml/admin/discountPage.fxml");
        });

        btnPayment.setOnAction(event -> {
            btnPayment.getScene().getWindow().hide();
            new Scene("Оплата", "fxml/admin/paymentPage.fxml");
        });

        btnStudents.setOnAction(event -> {
            btnStudents.getScene().getWindow().hide();
            new Scene("Студенты", "fxml/admin/studentsPage.fxml");
        });

        btnTeachers.setOnAction(event -> {
            btnTeachers.getScene().getWindow().hide();
            new Scene("Учителя", "fxml/admin/teachersPage.fxml");
        });

        btnGroups.setOnAction(event -> {
            btnGroups.getScene().getWindow().hide();
            new Scene("Группы", "fxml/admin/groupsPage.fxml");
        });

        btnCourse.setOnAction(event -> {
            btnCourse.getScene().getWindow().hide();
            new Scene("Курсы", "fxml/admin/coursesPage.fxml");
        });

        btnUsers.setOnAction(event -> {
            btnUsers.getScene().getWindow().hide();
            new Scene("Пользователи", "fxml/admin/usersPage.fxml");
        });

    }


    public void login(Boolean isAdmin) {
        if (isAdmin) {
            new Scene("Студенты", "fxml/admin/studentsPage.fxml");
        } else {
            new Scene("Группы", "fxml/user/groupsTeacherPage.fxml");
        }
    }

    public void showTable(TableView table, String nameTable) {
        StackPane tableLayout = new StackPane();
        tableLayout.getChildren().add(table);
        javafx.scene.Scene tableScene = new javafx.scene.Scene(tableLayout, 610, 400);
        tableStage = new Stage();
        tableStage.setTitle(nameTable);
        tableStage.setScene(tableScene);
        tableStage.initModality(Modality.WINDOW_MODAL);
        tableStage.show();
    }

    protected void setSelectedFieldCloseWindow(TextField field, String selectedItem) {
        tableStage.setOnCloseRequest(event -> field.setText(selectedItem));
    }

    protected Stage getTableStage() {
        return tableStage;
    }


}

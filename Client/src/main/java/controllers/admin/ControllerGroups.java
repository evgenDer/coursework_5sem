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
import model.Course;
import model.Group;
import model.Teacher;
import org.controlsfx.control.CheckListView;
import utils.Transformer;
import validation.Validation;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ControllerGroups extends Controller {

    @FXML
    private CheckListView<String> listDaysOfWeek;

    @FXML
    private Button btnAddGroup;

    @FXML
    private TextField fieldGroupStartLesson;

    @FXML
    private TextField fieldGroupAddIdCourse;

    @FXML
    private TextField fieldGroupAddIdTeacher;

    @FXML
    private Button btnAddTeacherId;

    @FXML
    private Button btnAddCourseId;

    @FXML
    private TextField fieldGroupDelete;

    @FXML
    private Button btnDeleteGroup;

    @FXML
    private TextArea textFieldReasonDeleteGroup;

    @FXML
    private Button btnForDeleteGroupNumber;

    @FXML
    private TextField fieldGroupIdEdit;

    @FXML
    private TextField fieldGroupEditIdTeacher;

    @FXML
    private TextField fieldGroupEditIdCourse;

    @FXML
    private Button btnEditGroupCourse;

    @FXML
    private Button btnEditGroupTeacher;

    @FXML
    private Button btnEditTeacherId;

    @FXML
    private Button btnEditCourseId;

    @FXML
    private Button btnEditGroupNumber;

    @FXML
    private TableView<Group> tableShowGroups;

    @FXML
    private TableView<Teacher> tableTeachers;

    @FXML
    private TableView<Course> tableCourses;

    Client clientHandler = Client.getInstance();

    @FXML
    void initialize() {
        super.init();
        listDaysOfWeek.setItems(WEEKDAYS);

        btnAddTeacherId.setOnAction(event -> showTableTeachers(fieldGroupAddIdTeacher));

        btnEditTeacherId.setOnAction(event -> showTableTeachers(fieldGroupEditIdTeacher));

        btnAddCourseId.setOnAction(event -> showTableCourses(fieldGroupAddIdCourse));

        btnEditCourseId.setOnAction(event -> showTableCourses(fieldGroupEditIdCourse));

        btnAddGroup.setOnAction(event -> addGroups());

        btnDeleteGroup.setOnAction(event -> deleteGroup());

        btnEditGroupCourse.setOnAction(event -> editCourse());

        btnEditGroupTeacher.setOnAction(event -> editTeacher());

        btnEditGroupNumber.setOnAction(event -> showTableGroups(fieldGroupIdEdit));

        btnForDeleteGroupNumber.setOnAction(event -> showTableGroups(fieldGroupDelete));
    }

    public void editCourse() {
        if (!Validation.hasEmptyFields(fieldGroupEditIdCourse, fieldGroupIdEdit)) {
            clientHandler.sendMessage("updateByCourse");
            clientHandler.sendObject(Integer.parseInt(fieldGroupIdEdit.getText()));
            clientHandler.sendObject(Integer.parseInt(fieldGroupEditIdCourse.getText()));
            fieldGroupIdEdit.setText("");
            fieldGroupEditIdCourse.setText("");
            AlertBox alert = new AlertBox("success", "Курс был изменен!");
            alert.show();
        } else {
            AlertBox alert = new AlertBox("warning", "Пожалуйста, выберите группу и преподователя!");
            alert.show();
        }
    }

    public void editTeacher() {
        if (!Validation.hasEmptyFields(fieldGroupEditIdTeacher, fieldGroupIdEdit)) {
            clientHandler.sendMessage("updateByTeacher");
            clientHandler.sendObject(Integer.parseInt(fieldGroupIdEdit.getText()));
            clientHandler.sendObject(Integer.parseInt(fieldGroupEditIdTeacher.getText()));
            fieldGroupIdEdit.setText("");
            fieldGroupEditIdTeacher.setText("");
            AlertBox alert = new AlertBox("success", "Преподователь был изменен!");
            alert.show();
        } else {
            AlertBox alert = new AlertBox("warning", "Пожалуйста, выберите группу и преподователя!");
            alert.show();
        }
    }

    public void deleteGroup() {
        if (!Validation.hasEmptyFields(fieldGroupDelete) && !Validation.hasEmptyFields(fieldGroupDelete)) {
            clientHandler.sendMessage("removeGroup");
            clientHandler.sendObject(Integer.parseInt(fieldGroupDelete.getText()));
            clientHandler.sendObject(textFieldReasonDeleteGroup.getText());
            textFieldReasonDeleteGroup.setText("");
            fieldGroupDelete.setText("");
            AlertBox alert = new AlertBox("success", "Пользователь был успешно удален!");
            alert.show();
        } else {
            AlertBox alert = new AlertBox("warning", "Пожалуйста, заполните все поля!");
            alert.show();
        }
    }

    public void fillGroupsTableData(TableView<Group> table, TableColumn<Group, ?>... columns) {
        ObservableList<Group> listGroups = FXCollections.observableArrayList();
        clientHandler.sendMessage("findAllGroups");

        int sizeList = (Integer) clientHandler.readObject();

        for (int i = 0; i < sizeList; i++) {
            Group group = (Group) clientHandler.readObject();
            listGroups.add(group);
        }

        columns[0].setCellValueFactory(new PropertyValueFactory("idGroup"));
        columns[1].setCellValueFactory(new PropertyValueFactory("startLesson"));
        columns[2].setCellValueFactory(new PropertyValueFactory("classDays"));
        columns[3].setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getCourse().getName()));
        columns[4].setCellValueFactory(new PropertyValueFactory("level"));
        columns[5].setCellValueFactory(data -> new SimpleObjectProperty(data.getValue()
                .getTeacher().getUser().getName()));

        table.setItems(listGroups);
    }

    public void showTableGroups(TextField fillField) {
        tableShowGroups = new TableView<>();
        tableShowGroups.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Group, ?> columnIdGroup = new TableColumn<>("№ группы");
        TableColumn<Group, ?> columnTime = new TableColumn<>("Время");
        TableColumn<Group, ?> columnWeekdays = new TableColumn<>("Дни недели");
        TableColumn<Group, ?> columnNameCourse = new TableColumn<>("Название курса");
        TableColumn<Group, ?> columnLevel = new TableColumn<>("Уровень");
        TableColumn<Group, ?> columnNameTeacher = new TableColumn<>("Имя учителя");

        tableShowGroups.getColumns().addAll(columnIdGroup, columnTime,
                columnWeekdays, columnNameCourse, columnLevel, columnNameTeacher);

        fillGroupsTableData(tableShowGroups, columnIdGroup, columnTime,
                columnWeekdays, columnNameCourse, columnLevel, columnNameTeacher);
        super.showTable(tableShowGroups, "Таблица групп");
        super.getTableStage().setOnCloseRequest(event -> {
            if (tableShowGroups.getItems().size() > 0 && tableShowGroups.getSelectionModel().getSelectedIndex() != -1) {
                fillField.setText(String.valueOf(tableShowGroups.getSelectionModel()
                        .getSelectedItem().getIdGroup()));
            } else {
                AlertBox alert = new AlertBox("warning", "Добавьте группы!");
                alert.show();
            }
        });
    }

    public void showTableTeachers(TextField fillField) {
        try {
            ControllerTeachers controllerTeachers = new ControllerTeachers();
            tableTeachers = new TableView<>();
            tableTeachers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            TableColumn<Teacher, String> columnId
                    = new TableColumn<>("№ пользователя");

            TableColumn<Teacher, String> name
                    = new TableColumn<>("Имя");
            TableColumn<Teacher, String> login
                    = new TableColumn<>("Логин");

            TableColumn<Teacher, String> university
                    = new TableColumn<>("Университет");

            tableTeachers.getColumns().addAll(columnId, name, login, university);

            controllerTeachers.fillDataTeachers(tableTeachers, columnId, name, login, university);
            super.showTable(tableTeachers, "Таблица учителей");
            super.getTableStage().setOnCloseRequest(event -> {
                if (tableTeachers.getItems().size() > 0 && tableTeachers.getSelectionModel().getSelectedIndex() != -1) {
                    fillField.setText(String.valueOf(tableTeachers.getSelectionModel()
                            .getSelectedItem().getIdTeacher()));
                } else {
                    AlertBox alert = new AlertBox("warning", "Вам ещё не назначили группы!");
                    alert.show();
                }
            });

        } catch (Exception exc) {
            AlertBox alert = new AlertBox("warning", "Добавьте учителей!");
            alert.show();
        }
    }

    public void showTableCourses(TextField fillField) {
        try {
            ControllerCourses controllerCourses = new ControllerCourses();
            tableCourses = new TableView<>();
            tableCourses.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            TableColumn<Course, String> columnId
                    = new TableColumn<>("№ курса");

            TableColumn<Course, String> columnName
                    = new TableColumn<>("Название");
            TableColumn<Course, String> columnCost
                    = new TableColumn<>("Цена");

            TableColumn<Course, String> columnLevel
                    = new TableColumn<>("Уровень");

            tableCourses.getColumns().addAll(columnId, columnName, columnCost, columnLevel);

            controllerCourses.fillDataCourses(tableCourses, columnId, columnName, columnCost, columnLevel);
            super.showTable(tableCourses, "Таблица курсов");


            super.getTableStage().setOnCloseRequest(event -> {
                if (tableCourses.getItems().size() > 0 && tableCourses.getSelectionModel().getSelectedIndex() != -1) {
                    fillField.setText(String.valueOf(tableCourses.getSelectionModel()
                            .getSelectedItem().getIdCourse()));
                }
            });
        } catch (Exception exc) {
            AlertBox alert = new AlertBox("warning", "Добавьте курс!");
            alert.show();
        }
    }

    public void addGroups() {
        try {
            if (!Validation.hasEmptyFields(fieldGroupStartLesson, fieldGroupAddIdCourse, fieldGroupAddIdTeacher)
                    && listDaysOfWeek.getCheckModel().getCheckedIndices().size() > 0) {
                if (Validation.hasCorrectTime(fieldGroupStartLesson)) {
                    Group group = new Group();
                    Date startLesson = new SimpleDateFormat("HH:mm").parse(fieldGroupStartLesson.getText());
                    Time time = new Time(startLesson.getTime());
                    group.setStartLesson(time);
                    group.setClassDays(Transformer.getStringFromArrayList(listDaysOfWeek.getCheckModel()
                            .getCheckedIndices(), ""));

                    group.getCourse().setIdCourse(Integer.parseInt(fieldGroupAddIdCourse.getText()));
                    group.getTeacher().setIdTeacher(Integer.parseInt(fieldGroupAddIdTeacher.getText()));

                    clientHandler.sendMessage("addGroup");
                    clientHandler.sendObject(group);
                    fieldGroupAddIdCourse.setText("");
                    fieldGroupAddIdTeacher.setText("");
                    fieldGroupStartLesson.setText("");
                    listDaysOfWeek.setItems(WEEKDAYS);
                    AlertBox alert = new AlertBox("success", "Вы успешно добавили группу!");
                    alert.show();
                }
            } else {
                AlertBox alert = new AlertBox("warning", "Пожалуйста, заполните все поля");
                alert.show();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}

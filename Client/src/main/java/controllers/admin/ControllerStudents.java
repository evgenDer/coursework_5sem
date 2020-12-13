package controllers.admin;

import client.Client;
import components.AlertBox;
import controllers.Controller;
import controllers.admin.ControllerGroups;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Student;
import org.controlsfx.control.CheckComboBox;
import validation.Validation;
import java.sql.Date;

public class ControllerStudents extends Controller {
    @FXML
    public TextArea fieldFeedback;

    @FXML
    private Button btnAddStudent;

    @FXML
    private TextField fieldStudentName;

    @FXML
    private TextField fieldStudentSurname;

    @FXML
    private TextField fieldStudentEmail;

    @FXML
    private TextField fieldNumberGroupAdd;

    @FXML
    private DatePicker fieldStudentDate;

    @FXML
    private Button btnAddGroupId;

    @FXML
    private ComboBox<String> boxAddLevel;

    @FXML
    private TextField fieldStudentIdDelete;

    @FXML
    private Button btnDeleteStudent;

    @FXML
    private Button btnDeleteStudentId;

    @FXML
    private Tab tabEditStudent;

    @FXML
    private TextField fieldStudentIdEdit;

    @FXML
    private TextField fieldGroupIdEdit;

    @FXML
    private Button btnEditLevelStudent;

    @FXML
    private Button btnEditGroupStudent;

    @FXML
    private Button btnEditStudentId;

    @FXML
    private Button btnEditGroupId;

    @FXML
    private ComboBox<String> boxEditLevel;

    @FXML
    private Tab tabShowStudents;

    @FXML
    private TableView<Student> tableShowStudents;

    @FXML
    private TableColumn<?, ?> tableShow;

    @FXML
    private Button btnFilterLevelStudent;

    @FXML
    private CheckComboBox<String> boxFilterLevel;

    Client clientHandler = Client.getInstance();

    @FXML
    void initialize() {
        super.init();
        boxFilterLevel.getItems().addAll(LANGUAGE_LEVELS);
        boxAddLevel.setItems(LANGUAGE_LEVELS);
        boxEditLevel.setItems(LANGUAGE_LEVELS);

        btnAddStudent.setOnAction(event -> addStudent());
        btnAddGroupId.setOnAction(event -> showGroupTable(fieldNumberGroupAdd));
        btnEditGroupId.setOnAction(event -> showGroupTable(fieldGroupIdEdit));
        tabShowStudents.setOnSelectionChanged(event -> {
            generateTableStudents(tableShowStudents);
        });

        btnFilterLevelStudent.setOnAction(event -> {
            filterTable();
        });

        btnDeleteStudentId.setOnAction(event -> showTableStudents(fieldStudentIdDelete));
        btnEditStudentId.setOnAction(event -> showTableStudents(fieldStudentIdEdit));
        btnDeleteStudent.setOnAction(event -> deleteStudent());
        btnEditGroupStudent.setOnAction(event -> editGroupStudent());
        btnEditLevelStudent.setOnAction(event -> editLevelStudent());

    }

    public void editLevelStudent() {
        if (!Validation.hasEmptyFields(fieldStudentIdEdit) && !Validation.hasEmptyFields(boxEditLevel)) {
            clientHandler.sendMessage("updateStudentLevel");
            clientHandler.sendObject(Integer.parseInt(fieldStudentIdEdit.getText()));
            clientHandler.sendObject(boxEditLevel.getSelectionModel().getSelectedItem());
            fieldStudentIdEdit.setText("");
            AlertBox alert = new AlertBox("success", "Уровень был отредактирован!");
            alert.show();
        } else {
            AlertBox alert = new AlertBox("warning", "Заполните поля номер студента и уровень!");
            alert.show();
        }
    }

    public void editGroupStudent() {
        if (!Validation.hasEmptyFields(fieldStudentIdEdit, fieldGroupIdEdit)) {
            clientHandler.sendMessage("updateStudentGroup");
            clientHandler.sendObject(Integer.parseInt(fieldStudentIdEdit.getText()));
            clientHandler.sendObject(Integer.parseInt(fieldGroupIdEdit.getText()));
            fieldStudentIdEdit.setText("");
            fieldGroupIdEdit.setText("");
            AlertBox alert = new AlertBox("success", "Группа была отредактированы!");
            alert.show();
        } else {
            AlertBox alert = new AlertBox("warning", "Заполните поля номера студента и группы!");
            alert.show();
        }
    }

    public void deleteStudent() {
        if (!Validation.hasEmptyFields(fieldStudentIdDelete) && !Validation.hasEmptyFields(fieldFeedback)) {
            clientHandler.sendMessage("removeStudent");
            clientHandler.sendObject(Integer.parseInt(fieldStudentIdDelete.getText()));
            clientHandler.sendObject(fieldFeedback.getText());
            fieldStudentIdDelete.setText("");
            fieldFeedback.setText("");
            AlertBox alert = new AlertBox("success", "Удаление группы прошло успешно!");
            alert.show();
        } else {
            AlertBox alert = new AlertBox("warning", "Заполните все поля!");
            alert.show();
        }
    }

    public void showGroupTable(TextField fillField) {
        ControllerGroups groupController = new ControllerGroups();
        groupController.showTableGroups(fillField);
    }

    public void filterTable() {
        generateTableStudents(tableShowStudents);
        FilteredList<Student> filteredData = new FilteredList<>(tableShowStudents.getItems(), s -> true);
        filteredData.setPredicate(s -> boxFilterLevel.getCheckModel().getCheckedItems().contains(s.getLevel()));
        tableShowStudents.setItems(filteredData);
    }

    public void generateTableStudents(TableView table) {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Student, ?> columnIdStudent = new TableColumn<>("Номер студента");
        TableColumn<Student, ?> columnSurname = new TableColumn<>("Фамилия");
        TableColumn<Student, ?> columnName = new TableColumn<>("Имя");
        TableColumn<Student, ?> columnEmail = new TableColumn<>("Почта");
        TableColumn<Student, ?> columnLevel = new TableColumn<>("Уровень");
        TableColumn<Student, ?> columnIdGroup = new TableColumn<>("Номер группы");
        TableColumn<Student, ?> dateStart = new TableColumn<>("Дата начала");

        if (table.getColumns().size() == 0) {
            table.getColumns().addAll(columnIdStudent, columnSurname,
                    columnName, columnEmail, columnLevel, columnIdGroup, dateStart);
        }
        fillDataStudents(table, columnIdStudent, columnSurname,
                columnName, columnEmail, columnLevel, columnIdGroup, dateStart);

    }

    public void showTableStudents(TextField fillField) {
        TableView<Student> tableShowStudents = new TableView<>();
        generateTableStudents(tableShowStudents);
        super.showTable(tableShowStudents, "Таблица студентов");
        super.getTableStage().setOnCloseRequest(event -> {
            if (tableShowStudents.getItems().size() > 0 && tableShowStudents.getSelectionModel().getSelectedIndex() != -1) {
                fillField.setText(String.valueOf(tableShowStudents.getSelectionModel()
                        .getSelectedItem().getIdStudent()));
            } else {
                AlertBox alert = new AlertBox("warning", "Добавьте студентов!");
                alert.show();
            }
        });
    }

    public void fillDataStudents(TableView table, TableColumn<Student, ?>... columns) {
        ObservableList<Student> listStudents = FXCollections.observableArrayList();
        clientHandler.sendMessage("findAllStudents");

        int sizeList = (Integer) clientHandler.readObject();
        for (int i = 0; i < sizeList; i++) {
            Student student = (Student) clientHandler.readObject();
            listStudents.add(student);
        }

        columns[0].setCellValueFactory(new PropertyValueFactory("idStudent"));
        columns[1].setCellValueFactory(new PropertyValueFactory("surname"));
        columns[2].setCellValueFactory(new PropertyValueFactory("name"));
        columns[3].setCellValueFactory(new PropertyValueFactory("email"));
        columns[4].setCellValueFactory(new PropertyValueFactory("level"));
        columns[5].setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getGroup().getIdGroup()));
        columns[6].setCellValueFactory(new PropertyValueFactory("dateStart"));

        table.setItems(listStudents);
    }

    public void addStudent() {
        if (validationFieldsForAddStudent()) {
            Student student = new Student();
            student.setName(fieldStudentName.getText());
            student.setSurname(fieldStudentSurname.getText());
            student.setEmail(fieldStudentEmail.getText());
            student.setDateStart(Date.valueOf(fieldStudentDate.getValue()));
            student.setLevel(boxAddLevel.getSelectionModel().getSelectedItem());
            student.getGroup().setIdGroup(Integer.parseInt(fieldNumberGroupAdd.getText()));
            fieldStudentSurname.setText("");
            fieldStudentName.setText("");
            fieldStudentEmail.setText("");
            fieldNumberGroupAdd.setText("");
            clientHandler.sendMessage("addStudent");
            clientHandler.sendObject(student);
        }

    }

    public Boolean validationFieldsForAddStudent() {
        if (Validation.hasEmptyFields(fieldStudentEmail, fieldNumberGroupAdd, fieldStudentName, fieldStudentSurname)
                && Validation.hasEmptyFields(boxAddLevel)) {
            AlertBox alert = new AlertBox("warning", "Пожалуйста, заполните все поля");
            alert.show();
            return false;
        }

        if (!Validation.hasCorrectEmail(fieldStudentEmail)) {
            return false;
        }

        if (!Validation.hasCorrectName(fieldStudentName) && !Validation.hasCorrectName(fieldStudentSurname)) {
            AlertBox alert = new AlertBox("error",
                    "Имя и фамилия может содержать только латинские или русские буквы. " +
                            "Первая буква должна быть заглавной!");
            alert.show();
            return false;
        }
        return true;

    }

}

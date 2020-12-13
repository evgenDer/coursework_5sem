package controllers.user;

import client.Client;
import components.AlertBox;
import controllers.Controller;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Group;
import javafx.scene.chart.BarChart;
import model.Student;

import java.util.List;

public class ControllerAttendance extends Controller {

    @FXML
    private TabPane coursePane;

    @FXML
    private Button btnChooseGroup;

    @FXML
    private TableView<Student> tableAttendance;

    @FXML
    private Button btnPlotChart;

    @FXML
    private BarChart<String, Number> chartAttendance;

    @FXML
    private TableColumn<?, ?> columnSurname;

    @FXML
    private TableColumn<?, ?> columnName;

    @FXML
    private TableColumn<Student, Boolean> columnAttendance;

    @FXML
    TableView<Group> tableGroup = new TableView<>();

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    TableColumn<Student, ?> columnIdStudent;

    private final Client clientHandler = Client.getInstance();

    @FXML
    void initialize(){
        initUser();
    }

    @FXML
    void showTableGroups() {
        new ControllerGroup().fillTableGroups(tableGroup);
        System.out.println();
        super.showTable(tableGroup, "Таблица групп");
        super.getTableStage().setOnCloseRequest(event -> {
            if (tableGroup.getItems().size() > 0 && tableGroup.getSelectionModel().getSelectedIndex() != -1) {
                clientHandler.sendMessage("findStudentAttendances");
                clientHandler.sendObject(tableGroup.getSelectionModel().getSelectedItem().getIdGroup());
                ObservableList<Student> listStudents = FXCollections.observableArrayList();
                listStudents.addAll((List<Student>) clientHandler.readObject());
                generateTable();
                tableAttendance.setItems(listStudents);
            } else {
                AlertBox alert = new AlertBox("warning", "Выберите группу!");
                alert.show();
            }
        });
    }

    @FXML
    void plotChart() {
        new ControllerGroup().fillTableGroups(tableGroup);
        super.showTable(tableGroup, "Таблица групп");
        super.getTableStage().setOnCloseRequest(event -> {
            if (tableGroup.getItems().size() > 0 && tableGroup.getSelectionModel().getSelectedIndex() != -1) {
                clientHandler.sendMessage("findStudentAttendances");
                clientHandler.sendObject(tableGroup.getSelectionModel().getSelectedItem().getIdGroup());
                List<Student> studentList = (List<Student>) clientHandler.readObject();
                XYChart.Series<String, Number> studentData = new XYChart.Series<>();
                chartAttendance.setLegendVisible(false);

                for (int i = 0; i < studentList.size(); i++) {
                    Student currentStudent = studentList.get(i);
                    studentData.getData().add(new XYChart.Data<>(currentStudent.getName(),
                            currentStudent.getCountAttendant()));
                }

                if (studentList.size() > 0) {
                    chartAttendance.getData().add(studentData);
                }

            } else {
                AlertBox alert = new AlertBox("warning", "Выберите группу!");
                alert.show();
            }
        });
    }

    public void generateTable() {
        columnIdStudent.setCellValueFactory(new PropertyValueFactory("idStudent"));
        columnName.setCellValueFactory(new PropertyValueFactory("name"));
        columnSurname.setCellValueFactory(new PropertyValueFactory("surname"));
        columnAttendance.setCellValueFactory(param -> {
            Student student = param.getValue();
            SimpleBooleanProperty booleanProp = new SimpleBooleanProperty();
            booleanProp.addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    student.setCountAttendant(student.getCountAttendant() + 1);
                } else {
                    student.setCountAttendant(student.getCountAttendant() - 1);
                }
                clientHandler.sendMessage("updateStudentAttendance");
                clientHandler.sendObject(student);
            });

            return booleanProp;
        });

        columnAttendance.setCellFactory(p -> {
            CheckBoxTableCell<Student, Boolean> cell = new CheckBoxTableCell<Student, Boolean>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });
    }

}




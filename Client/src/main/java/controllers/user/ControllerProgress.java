package controllers.user;

import client.Client;
import components.AlertBox;
import controllers.Controller;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Student;
import model.Test;

import java.awt.event.ActionEvent;
import java.time.LocalDate;

public class ControllerProgress extends Controller {

    @FXML
    private Button btnGroups;

    @FXML
    private Button btnSchedule;

    @FXML
    private Button btnTests;

    @FXML
    private Button btnProgress;

    @FXML
    private Button btnAttendance;

    @FXML
    private Button btnExit;

    @FXML
    private TabPane coursePane;

    @FXML
    private Button btnChooseStudent;

    @FXML
    private Label labelAverageScore;

    @FXML
    private Label labelName;

    @FXML
    private TableView<Test> tableResults;

    @FXML
    private TableColumn<?, ?> columnResult;

    @FXML
    private TableColumn<?, ?> columnDate;

    @FXML
    private Label labelSurname;

    @FXML
    private LineChart<Number, Number> chartStudent;

    @FXML
    private TableColumn<?, ?> columnIdTest;

    @FXML
    private Button btnPlotChart;

    private final Client clientHandler = Client.getInstance();

    TableView<Student> tableStudents = new TableView<>();

    @FXML
    void initialize() {
        initUser();
    }

    @FXML
    void plotChart() {
        generateTableStudents();
        super.showTable(tableStudents, "Таблица студентов");
        super.getTableStage().setOnCloseRequest(event -> {
            if (tableStudents.getItems().size() > 0 && tableStudents.getSelectionModel().getSelectedIndex() != -1) {
                clientHandler.sendMessage("findProgress");
                Student selectedStudent = tableStudents.getSelectionModel().getSelectedItem();
                clientHandler.sendObject(selectedStudent.getIdStudent());
                Student student = (Student) clientHandler.readObject();
                XYChart.Series<Number, Number> studentData = new XYChart.Series<>();
                chartStudent.setLegendVisible(false);

                for (int i = 0; i < student.getListTests().size(); i++) {
                    Test test = student.getListTests().get(i);
                    studentData.getData().add(new XYChart.Data<>(test.getIdTest(),
                            test.getResultTest()));
                }

                if (student.getListTests().size() > 0) {
                    chartStudent.getData().add(studentData);
                }
            } else {
                AlertBox alert = new AlertBox("warning", "Выберите студента!");
                alert.show();
            }
        });
    }

    @FXML
    void showTableStudents() {
        generateTableStudents();
        super.showTable(tableStudents, "Таблица студентов");
        super.getTableStage().setOnCloseRequest(event -> {
            if (tableStudents.getItems().size() > 0 && tableStudents.getSelectionModel().getSelectedIndex() != -1) {
                clientHandler.sendMessage("findProgress");
                Student selectedStudent = tableStudents.getSelectionModel().getSelectedItem();
                clientHandler.sendObject(selectedStudent.getIdStudent());
                ObservableList<Test> testStudents = FXCollections.observableArrayList();
                Student student = (Student) clientHandler.readObject();
                columnIdTest.setCellValueFactory(new PropertyValueFactory("idTest"));
                columnResult.setCellValueFactory(new PropertyValueFactory("resultTest"));
                columnDate.setCellValueFactory(new PropertyValueFactory("date"));
                labelName.setText("Имя: " +  selectedStudent.getName());
                labelSurname.setText("Фамилия: " +  selectedStudent.getSurname());
                labelAverageScore.setText("Средний балл: " +  student.getAverageScore());
                testStudents.addAll(student.getListTests());
                tableResults.setItems(testStudents);
            } else {
                AlertBox alert = new AlertBox("warning", "Выберите студента!");
                alert.show();
            }
        });
    }

    public void generateTableStudents() {
        tableStudents = new TableView<>();
        tableStudents.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<Student> listStudents = FXCollections.observableArrayList();
        clientHandler.sendMessage("findAllStudents");
        clientHandler.sendObject(clientHandler.getLogin());

        int sizeList = (Integer) clientHandler.readObject();
        for (int i = 0; i < sizeList; i++) {
            Student student = (Student) clientHandler.readObject();
            listStudents.add(student);
        }
        TableColumn<Student, ?> columnIdStudent = new TableColumn<>("Номер студента");
        TableColumn<Student, ?> columnSurname = new TableColumn<>("Фамилия");
        TableColumn<Student, ?> columnName = new TableColumn<>("Имя");
        TableColumn<Student, ?> columnLevel = new TableColumn<>("Уровень");
        TableColumn<Student, ?> columnIdGroup = new TableColumn<>("Номер группы");

        columnIdStudent.setCellValueFactory(new PropertyValueFactory("idStudent"));
        columnSurname.setCellValueFactory(new PropertyValueFactory("surname"));
        columnName.setCellValueFactory(new PropertyValueFactory("name"));
        columnLevel.setCellValueFactory(new PropertyValueFactory("level"));
        columnIdGroup.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getGroup().getIdGroup()));
        tableStudents.getColumns().addAll(columnIdStudent, columnSurname, columnName, columnLevel, columnIdGroup);
        tableStudents.setItems(listStudents);
    }

}

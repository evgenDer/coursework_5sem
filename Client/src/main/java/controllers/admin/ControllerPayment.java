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
import model.Discount;
import model.Payment;
import model.Student;
import validation.Validation;

public class ControllerPayment extends Controller {

    @FXML
    private Button btnPay;

    @FXML
    private TextField fieldIdStudent;

    @FXML
    private TextField fieldCostPay;

    @FXML
    private TextField fieldDiscountPay;

    @FXML
    private TextField fieldContributionPay;

    @FXML
    private TextField fieldChange;

    @FXML
    private Button btnStudentId;

    @FXML
    private Tab tabDebtors;

    @FXML
    private TableView<Student> tableDebtors;

    @FXML
    private TableColumn<Student, ?> columnId;

    @FXML
    private TableColumn<Student, ?> columnName;

    @FXML
    private TableColumn<Student, ?> columnSurname;

    @FXML
    private TableColumn<Student, ?> columnGroup;

    @FXML
    private TableColumn<Student, ?> columnEmail;

    @FXML
    private Button btnSend;


    Client clientHandler = Client.getInstance();

    @FXML
    void initialize() {
        super.init();
        btnStudentId.setOnAction(event -> {
            ControllerStudents controllerStudents = new ControllerStudents();
            controllerStudents.showTableStudents(fieldIdStudent);
        });

        fieldIdStudent.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!fieldIdStudent.getText().equals("")) {
                clientHandler.sendMessage("findPayment");
                clientHandler.sendObject(Integer.parseInt(fieldIdStudent.getText()));
                Payment payment = (Payment) clientHandler.readObject();
                fieldCostPay.setText(String.valueOf(payment.getCost()));
                fieldDiscountPay.setText(String.valueOf(payment.getDiscount().getValueDiscount()));
            }
        });

        fieldContributionPay.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!fieldIdStudent.getText().equals("") && Validation.hasCorrectDouble(fieldContributionPay)) {
                Discount discount = new Discount();
                discount.setValueDiscount(1 - Double.parseDouble(fieldDiscountPay.getText()) / 100);
                Double change = Double.parseDouble(fieldContributionPay.getText()) -
                        Double.parseDouble(fieldCostPay.getText()) *discount.getValueDiscount();
                fieldChange.setText(String.format("%.2f", change));
            }
        });
        btnPay.setOnAction(event -> {
            if (!Validation.hasEmptyFields(fieldContributionPay, fieldIdStudent)) {
                clientHandler.sendMessage("payStudent");
                clientHandler.sendObject(Integer.parseInt(fieldIdStudent.getText()));
                fieldIdStudent.setText("");
                fieldContributionPay.setText("");
                fieldCostPay.setText("");
                fieldChange.setText("");
                fieldDiscountPay.setText("");
                AlertBox alert = new AlertBox("success", "Оплата прошла успешно");
                alert.show();
            } else {
                AlertBox alert = new AlertBox("warning", "Пожалуйста, заполните поле студента" +
                        " и внесенные средства");
                alert.show();
            }
            clientHandler.sendMessage("findPayment");
        });

        tabDebtors.setOnSelectionChanged(event -> {
            ObservableList<Student> list = FXCollections.observableArrayList();
            clientHandler.sendMessage("findDebtors");

            int sizeList = (Integer) clientHandler.readObject();
            for (int i = 0; i < sizeList; i++) {
                Student student = (Student) clientHandler.readObject();
                list.add(student);
            }


            columnId.setCellValueFactory(new PropertyValueFactory("idStudent"));
            columnName.setCellValueFactory(new PropertyValueFactory("name"));
            columnSurname.setCellValueFactory(new PropertyValueFactory("surname"));
            columnEmail.setCellValueFactory(new PropertyValueFactory("email"));
            columnGroup.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getGroup().getIdGroup()));

            tableDebtors.setItems(list);
        });

        btnSend.setOnAction(event -> {
            try {
                Student selectedDebtor = tableDebtors.getSelectionModel().getSelectedItem();
                clientHandler.sendMessage("sendPaymentReminder");
                clientHandler.sendObject(selectedDebtor.getEmail());
                AlertBox alert = new AlertBox("success", "Напоминание было успешно отправлено!");
                alert.show();
            } catch (NullPointerException ex) {
                AlertBox alert = new AlertBox("warning",
                        "Пожалуйста, выберите студента для отправки напоминания");
                alert.show();
            }
        });
    }

}

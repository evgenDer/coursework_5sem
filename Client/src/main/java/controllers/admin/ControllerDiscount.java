package controllers.admin;

import client.Client;
import components.AlertBox;
import controllers.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Discount;
import validation.Validation;

public class ControllerDiscount extends Controller {

    @FXML
    private CheckBox isDiscountWithFriend;

    @FXML
    private CheckBox isDiscountWithFamily;

    @FXML
    private CheckBox isDiscountLongPeriod;

    @FXML
    private Spinner<Integer> additional;

    @FXML
    private TextField fieldResultDiscount;

    @FXML
    private Button btnCalculateDiscount;

    @FXML
    private TextField fieldStudentId;

    @FXML
    private Button btnStudentId;

    @FXML
    void initialize() {
        super.init();
        additional.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, 0));
        additional.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_LEFT_VERTICAL);
        btnStudentId.setOnAction(event -> {
            ControllerStudents controllerStudents = new ControllerStudents();
            controllerStudents.showTableStudents(fieldStudentId);
        });

        btnCalculateDiscount.setOnAction(event -> {
            if(!Validation.hasEmptyFields(fieldStudentId)) {
                Discount discount = new Discount();
                discount.setDiscountFamily(isDiscountWithFamily.isSelected());
                discount.setAdditionalDiscount(additional.getValue());
                discount.setDiscountFriends(isDiscountWithFriend.isSelected());
                discount.setDiscountDuration(isDiscountLongPeriod.isSelected());
                Client.getInstance().sendMessage("calculateDiscount");
                Client.getInstance().sendObject(Integer.parseInt(fieldStudentId.getText()));
                Client.getInstance().sendObject(discount);
                fieldResultDiscount.setText(Client.getInstance().readObject().toString());
            } else {
                AlertBox alert = new AlertBox("warning", "Выберите студента!");
                alert.show();
            }
        });
    }

}


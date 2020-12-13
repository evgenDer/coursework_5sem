package controllers.user;

import client.Client;
import controllers.Controller;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Group;
import org.controlsfx.control.CheckComboBox;


public class ControllerGroup extends Controller {
    @FXML
    private CheckComboBox<String> boxDayOfWeek;

    @FXML
    private TableView<Group> tableGroup;

    @FXML
    private Button btnShow;

    private final Client clientHandler = Client.getInstance();

    @FXML
    void initialize() {
        initUser();
        boxDayOfWeek.getItems().addAll(WEEKDAYS);
        fillTableGroups(tableGroup);
        btnShow.setOnAction(event -> filterTable());
    }

    public void filterTable() {
        getData(tableGroup);
        FilteredList<Group> filteredData = new FilteredList<>(tableGroup.getItems(), s -> true);
        filteredData.setPredicate(s -> {
            String checkedElements = boxDayOfWeek.getCheckModel().getCheckedItems().toString();
            return s.getClassDays().equals(checkedElements.substring(1, checkedElements.length() - 1));
        });
        if (boxDayOfWeek.getCheckModel().getCheckedItems().size() != 0) {
            tableGroup.setItems(filteredData);
        }
    }


    public void fillTableGroups(TableView<Group> tableGroup) {
        tableGroup.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Group, ?> columnIdGroup = new TableColumn<>("№ группы");
        TableColumn<Group, ?> columnTime = new TableColumn<>("Время");
        TableColumn<Group, ?> columnWeekdays = new TableColumn<>("Дни недели");
        TableColumn<Group, ?> columnNameCourse = new TableColumn<>("Название курса");
        TableColumn<Group, ?> columnLevel = new TableColumn<>("Уровень");

        columnIdGroup.setCellValueFactory(new PropertyValueFactory("idGroup"));
        columnTime.setCellValueFactory(new PropertyValueFactory("startLesson"));
        columnWeekdays.setCellValueFactory(new PropertyValueFactory("classDays"));
        columnNameCourse.setCellValueFactory(cellData ->
                new SimpleObjectProperty(cellData.getValue().getCourse().getName()));
        columnLevel.setCellValueFactory(new PropertyValueFactory("level"));

        getData(tableGroup);

        if (tableGroup.getColumns().size() == 0) {
            tableGroup.getColumns().addAll(columnIdGroup, columnTime,
                    columnWeekdays, columnNameCourse, columnLevel);
        }
    }

    public void getData(TableView tableGroup) {
        ObservableList<Group> listGroups = FXCollections.observableArrayList();
        clientHandler.sendMessage("findAllGroupTeacher");
        clientHandler.sendObject(clientHandler.getLogin());
        int sizeList = (Integer) clientHandler.readObject();
        for (int i = 0; i < sizeList; i++) {
            Group group = (Group) clientHandler.readObject();
            listGroups.add(group);
        }

        tableGroup.setItems(listGroups);
    }
}

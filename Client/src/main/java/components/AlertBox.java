package components;

import javafx.scene.control.Alert;

public class AlertBox {
    private String type;
    private String textContent;

    public AlertBox(String type, String textContent) {
        this.type = type;
        this.textContent = textContent;
    }

    public void show() {
        switch (type) {
            case "error": {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setContentText(textContent);
                alert.setHeaderText(null);
                alert.showAndWait();
            }
            break;
            case "warning": {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Предупреждение");
                alert.setContentText(textContent);
                alert.setHeaderText(null);
                alert.showAndWait();
            }
            break;
            case "success": {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Информация");
                alert.setContentText(textContent);
                alert.setHeaderText(null);
                alert.showAndWait();
            }
            break;
        }
    }

}

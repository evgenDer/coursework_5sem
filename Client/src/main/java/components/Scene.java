package components;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.io.IOException;

public class Scene {
    public Scene(String title, String window){
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(window));
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.hide();
            stage.setScene(new javafx.scene.Scene(root, 900, 680));
            stage.show();
        } catch (IOException e) {
//            Erors alert = new Erors(1,"Не удалось открыть окно" + window);
            e.printStackTrace();
        }
    }
}

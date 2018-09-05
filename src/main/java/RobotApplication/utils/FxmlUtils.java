package RobotApplication.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class FxmlUtils {


    public static Pane fxmlLoader(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader(FxmlUtils.class.getResource(fxmlPath));
        try {
            return loader.load();
        } catch (Exception e) {
            e.printStackTrace();
            DialogsUtils.errorDialog(e.getMessage());
        }
        return null;
    }


}

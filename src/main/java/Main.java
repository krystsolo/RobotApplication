import RobotApplication.utils.FxmlUtils;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    private static final String BORDER_PANE_MAIN_FXML = "/fxml/MainWindow.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        Pane borderPane = FxmlUtils.fxmlLoader(BORDER_PANE_MAIN_FXML);
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("RobotMaster v1.0");
        primaryStage.resizableProperty().set(false);
        primaryStage.show();

    }
}

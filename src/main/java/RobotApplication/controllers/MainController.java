package RobotApplication.controllers;

import RobotApplication.models.ProgramModel;
import RobotApplication.utils.FxmlUtils;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.util.Locale;

public class MainController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private DownBorderController downBorderController;

    @FXML
    private void initialize() {
        ProgramLeftBorderController.setMainController(this);
        this.downBorderController.setMainController(this);
        ProgramRightBorderController.setMainController(this);
        SimulationLeftBorderController.setMainController(this);

        ProgramModel programModel = new ProgramModel();
        programModel.recreateProgram(programModel.getTemporaryProgramName());
        Locale.setDefault(Locale.US);
    }

    public void setLeft(String fxmlPath) {
        borderPane.setLeft(FxmlUtils.fxmlLoader(fxmlPath));
    }

    public void setRight(String fxmlPath) {
        borderPane.setRight(FxmlUtils.fxmlLoader(fxmlPath));
    }

    public void setCenter(String fxmlPath) {
        borderPane.setCenter((Parent)FxmlUtils.fxmlLoader(fxmlPath));
    }

    public void setRightEmpty(){
        borderPane.setRight(null);
    }



}

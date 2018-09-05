package RobotApplication.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class DownBorderController {

    private static final String STEERAGE_LEFT_BORDER_FXML = "/fxml/SteerageLeftBorder.fxml";
    private static final String STEERAGE_RIGHT_BORDER_FXML = "/fxml/SteerageRightBorder.fxml";
    private static final String SIMULATION_LEFT_BORDER_FXML = "/fxml/SimulationLeftBorder.fxml";
    private static final String PROGRAM_LEFT_BORDER_FXML = "/fxml/ProgramLeftBorder.fxml";
    private static final String PROGRAM_RIGHT_BORDER_FXML = "/fxml/ProgramRightBorder.fxml";
    private static final String CENTER_WINDOW_FXML = "/fxml/CenterWindow.fxml";
    @FXML
    private ToggleButton bottomSteerageButton;
    @FXML
    private ToggleButton bottomProgramButton;

    private MainController mainController;

    @FXML
    public void initialize(){
        bottomProgramButton.disableProperty().bind(SimulationLeftBorderController.isInRunModeProperty());
        bottomSteerageButton.disableProperty().bind(SimulationLeftBorderController.isInRunModeProperty());
    }

    @FXML
    private ToggleGroup mainToggleGroup;

    public void steerageOpen() {
        mainController.setCenter(CENTER_WINDOW_FXML);
        mainController.setLeft(STEERAGE_LEFT_BORDER_FXML);
        mainController.setRight(STEERAGE_RIGHT_BORDER_FXML);
    }

    public void simulationOpen() {
        mainController.setCenter(CENTER_WINDOW_FXML);
        mainController.setLeft(SIMULATION_LEFT_BORDER_FXML);
        mainController.setRightEmpty();
    }

    public void programOpen() {
        mainController.setCenter(CENTER_WINDOW_FXML);
        mainController.setLeft(PROGRAM_LEFT_BORDER_FXML);
        mainController.setRight(PROGRAM_RIGHT_BORDER_FXML);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}

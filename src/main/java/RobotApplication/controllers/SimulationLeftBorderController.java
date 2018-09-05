package RobotApplication.controllers;

import RobotApplication.models.RobotAction;
import RobotApplication.models.RobotModel;
import RobotApplication.models.RobotMove;
import RobotApplication.models.TypeOfAction;
import RobotApplication.utils.ValidationData;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class SimulationLeftBorderController {

    private static final String CONTINUOUS_SIMULATION_WINDOW_FXML = "/fxml/ContinuousSimulationWindow.fxml";
    private static final String SIMULATION_RIGHT_BORDER_FXML = "/fxml/SimulationRightBorder.fxml";
    private static MainController mainController;
    private static BooleanProperty isInRunMode = new SimpleBooleanProperty(false);

    @FXML
    private Button continuousProgramButton;
    @FXML
    private Button stepByStepRunProgramButton;
    @FXML
    private TextField instructionToRunTextField;
    @FXML
    private Button runInstructionButton;

    private BooleanProperty isInstructionInTextFieldCorrect = new SimpleBooleanProperty(false);

    @FXML
    public void initialize() {
        RobotModel.chosenInstructionByClickProperty().addListener((observable, oldValue, newValue) -> {
            instructionToRunTextField.setText(String.valueOf(newValue.getInstructionNumber()));
        });
        instructionToRunTextField.textProperty().addListener(ValidationData.isFieldContainingNumberFrom1To999());
        instructionToRunTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            isInstructionInTextFieldCorrect.set(RobotModel.getOperationsList().stream().noneMatch(s -> s.getInstructionNumber() == Long.valueOf(newValue)));
        });
        runInstructionButton.disableProperty().bind(isInRunMode.or(isInstructionInTextFieldCorrect).or(instructionToRunTextField.textProperty().isEmpty()));
        continuousProgramButton.disableProperty().bind(isInRunMode.or(Bindings.isEmpty(RobotModel.getOperationsList())));
        stepByStepRunProgramButton.disableProperty().bind(isInRunMode.or(Bindings.isEmpty(RobotModel.getOperationsList())));
    }

    @FXML
    public void runContinuousProgramOnAction() {
        mainController.setRight(CONTINUOUS_SIMULATION_WINDOW_FXML);
        isInRunMode.set(true);
    }

    @FXML
    public void runProgramStepByStepOnAction() {
        mainController.setRight(SIMULATION_RIGHT_BORDER_FXML);
        isInRunMode.set(true);
    }

    @FXML
    public void runInstructionOnAction() {
        RobotModel robotModel = new RobotModel();
        RobotAction robotAction = robotModel.selectInstructionFromDataBaseById(Long.valueOf(instructionToRunTextField.getText()));
        robotAction.performOperation();
    }

    public static void setRightEmpty() {
        mainController.setRightEmpty();
        isInRunMode.set(false);
    }

    public static void setMainController(MainController mainController) {
        SimulationLeftBorderController.mainController = mainController;
    }

    public static BooleanProperty isInRunModeProperty() {
        return isInRunMode;
    }

}

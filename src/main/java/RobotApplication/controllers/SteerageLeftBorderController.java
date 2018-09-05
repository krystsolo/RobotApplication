package RobotApplication.controllers;

import RobotApplication.models.RobotAction;
import RobotApplication.models.RobotGripper;
import RobotApplication.models.RobotModel;
import RobotApplication.models.TypeOfAction;
import RobotApplication.utils.DialogsUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.Optional;

public class SteerageLeftBorderController {
    @FXML
    private Button helpButton;
    @FXML
    private Button gripperButton;
    @FXML
    private Label gripperStateLabel;
    @FXML
    private Button previousInstructionButton;
    @FXML
    private Button saveInstructionButton;

    @FXML
    private static SteerageRightBorderController steerageRightBorderController;

    @FXML
    public void initialize(){
        this.previousInstructionButton.disableProperty().bind(RobotModel.previousInstructionProperty().isNull());
        this.saveInstructionButton.disableProperty().bind(RobotModel.previousInstructionProperty().isNull());
        this.gripperStateLabel.textProperty().bind(RobotGripper.currentGripperPositionProperty());
    }

    @FXML
    public void helpOnAction( ) {
        DialogsUtils.helpDialog();
    }

    @FXML
    public void gripperOnAction( ) {
        TypeOfAction typeOfAction = reverseMoveToPresent();
        RobotAction robotAction = new RobotGripper(0, typeOfAction);
        robotAction.performOperation();
    }

    @FXML
    public void previousInstructionOnAction( ) {
        steerageRightBorderController.setFieldsWithPreviousInstructionParameters();
        RobotAction previousInstruction = RobotModel.getPreviousInstruction();
        previousInstruction.performOperation();
        RobotModel.setPreviousInstruction(null);
    }

    @FXML
    public void saveInstructionOnAction( ) {
        RobotAction robotAction = RobotModel.getPreviousInstruction();
        showWindowToSetInstructionNumber(robotAction);
    }

    public static void setSteerageRightBorderController(SteerageRightBorderController steerageRightBorderController) {
        SteerageLeftBorderController.steerageRightBorderController = steerageRightBorderController;
    }

    private TypeOfAction reverseMoveToPresent(){
        if(TypeOfAction.GO.name().equals(RobotGripper.getCurrentGripperPosition())){
            return TypeOfAction.GC;
        } else{
            return TypeOfAction.GO;
        }
    }

    private void showWindowToSetInstructionNumber(RobotAction robotAction) {
        RobotModel robotModel = new RobotModel();
        Optional result = DialogsUtils.addInstructionNumberDialog();
        if(result.isPresent()){
            robotAction.setInstructionNumber(Long.valueOf(result.get().toString()));
            robotModel.addInstructionToDataBase(robotAction);
        }
    }

}

package RobotApplication.controllers;

import RobotApplication.models.RobotAction;
import RobotApplication.models.RobotModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class SimulationRightBorderController {

    @FXML
    private Label actualInstructionLabel;
    @FXML
    private Button previousInstruction;
    @FXML
    private Button nextInstructionButton;
    @FXML
    private Button exitButton;

    private IntegerProperty indexOfActuallyRunningInstruction = new SimpleIntegerProperty(0);
    private ObjectProperty<RobotAction> actuallyRunningInstruction = new SimpleObjectProperty<>();
    private ObservableList<RobotAction> listOfInstructions = RobotModel.getOperationsList();

    @FXML
    public void initialize(){
        previousInstruction.disableProperty().bind(indexOfActuallyRunningInstruction.lessThan(1));
        nextInstructionButton.disableProperty().bind(indexOfActuallyRunningInstruction.greaterThan(listOfInstructions.size() - 2));
        actuallyRunningInstruction.bind(Bindings.valueAt(listOfInstructions, indexOfActuallyRunningInstruction));
        actualInstructionLabel.textProperty().bind(actuallyRunningInstruction.asString());
    }

    @FXML
    public void previousInstructionOnAction( ) {
        actuallyRunningInstruction.get().performOperation();
        indexOfActuallyRunningInstruction.set(indexOfActuallyRunningInstruction.get() - 1);

    }
    @FXML
    public void nextInstructionOnAction( ) {
        actuallyRunningInstruction.get().performOperation();
        System.out.println(actuallyRunningInstruction.get().toString());
        indexOfActuallyRunningInstruction.set(indexOfActuallyRunningInstruction.get() + 1);
        System.out.println(indexOfActuallyRunningInstruction);

    }
    @FXML
    public void exitOnAction( ) {
        SimulationLeftBorderController.setRightEmpty();
    }



}

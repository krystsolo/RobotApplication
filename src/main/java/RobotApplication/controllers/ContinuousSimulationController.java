package RobotApplication.controllers;

import RobotApplication.models.RobotAction;
import RobotApplication.models.RobotModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;



public class ContinuousSimulationController {
    @FXML
    private Button abortRunningButton;
    @FXML
    private Label actuallyContinuousLabel;

    private boolean isContinuousRunningAborted;
    private ObservableList<RobotAction> listOfInstructions = RobotModel.getOperationsList();
    private IntegerProperty indexOfActuallyRunningInstruction = new SimpleIntegerProperty(0);

    @FXML
    public void initialize(){
        actuallyContinuousLabel.textProperty().bind(Bindings.valueAt(listOfInstructions,indexOfActuallyRunningInstruction).asString());
    }

    @FXML
    public void abortRunningOnAction( ) {
        this.isContinuousRunningAborted = true;
        SimulationLeftBorderController.setRightEmpty();
    }

    @FXML
    public void startOnAction() {
        while(indexOfActuallyRunningInstruction.get() < listOfInstructions.size() && !isContinuousRunningAborted ){
            listOfInstructions.get(indexOfActuallyRunningInstruction.get()).performOperation();
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2000)));
            timeline.play();
            indexOfActuallyRunningInstruction.set(indexOfActuallyRunningInstruction.get() + 1);
        }
    }
}

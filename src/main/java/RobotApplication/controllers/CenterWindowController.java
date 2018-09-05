package RobotApplication.controllers;


import RobotApplication.models.RobotAction;
import RobotApplication.models.RobotModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class CenterWindowController {
    @FXML
    private ListView<RobotAction> OperationsListView;

    @FXML
    public void initialize(){
        RobotModel.setCenterWindowController(this);
        RobotModel robotModel = new RobotModel();
        robotModel.initOperationList();
        this.OperationsListView.setItems(RobotModel.getOperationsList());
    }

    public void choseInstructionOnAction() {
        this.OperationsListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                        RobotModel.chosenInstructionByClickProperty().set(newValue);
                });
    }

    public void refreshInstructionListView(ObservableList<RobotAction> list){
        this.OperationsListView.setItems(list);
    }






}
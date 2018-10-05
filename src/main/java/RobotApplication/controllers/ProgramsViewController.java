
package RobotApplication.controllers;

import RobotApplication.models.ProgramModel;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import static RobotApplication.controllers.TypeOfProgramActionEnum.*;


public class ProgramsViewController {
    private static TypeOfProgramActionEnum typeOfAction;
    private static ProgramLeftBorderController programLeftBorderController;

    @FXML
    private Label programViewLabel;
    @FXML
    private ListView<String> programsListView;
    @FXML
    private TextField newProgramNameTextField;
    @FXML
    private Button OkButton;
    @FXML
    private Button backButton;

    private ProgramModel programModel;

    @FXML
    public void initialize() {
        this.programModel = new ProgramModel();
        String operationTittle = ProgramsViewController.typeOfAction.toString();
        this.programViewLabel.setText(operationTittle);
        this.programModel.initProgramsList();
        programsListView.setItems(this.programModel.getProgramList());
        initBindings();
    }

    @FXML
    public void confirmOnAction() {
        String textFromFieldAsProgramName = newProgramNameTextField.getText();

        switch(typeOfAction) {
            case CREATE:
                programModel.createProgramInDataBase(textFromFieldAsProgramName);

            case DELETE:
                programModel.deleteProgramFromDataBase(textFromFieldAsProgramName);

            case LOAD:
                programModel.loadProgramFromDataBase(textFromFieldAsProgramName);

            case SAVE_AS:
                programModel.saveProgramAsToDataBase(textFromFieldAsProgramName);

            case CHANGE_NAME:
                String oldProgramName = programsListView.getSelectionModel().getSelectedItem();
                programModel.changeProgramName(oldProgramName, textFromFieldAsProgramName);
        }

        programLeftBorderController.loadCenterWindowFxml();
    }

    @FXML
    public void backOnAction() {
        programLeftBorderController.loadCenterWindowFxml();
    }

    public static void setTypeOfAction(TypeOfProgramActionEnum typeOfAction) {
        ProgramsViewController.typeOfAction = typeOfAction;
    }

    public static void setProgramLeftController(ProgramLeftBorderController programLeftBorderController) {
        ProgramsViewController.programLeftBorderController = programLeftBorderController;
    }

    private void initBindings() {
        OkButton.disableProperty().bind(newProgramNameTextField.textProperty().isEmpty());
        if( ! (typeOfAction == TypeOfProgramActionEnum.CHANGE_NAME)){
            programsListView.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> newProgramNameTextField.setText(newValue));

        } else {
            OkButton.disableProperty().bind(programsListView.getSelectionModel().selectedItemProperty().isNull());
        }
    }
}


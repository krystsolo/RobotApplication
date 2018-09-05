
package RobotApplication.controllers;

import RobotApplication.models.ProgramModel;

import javafx.fxml.FXML;
import javafx.scene.control.*;


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

        if (typeOfAction == TypeOfProgramActionEnum.CREATE) {
            programModel.createProgramInDataBase(textFromFieldAsProgramName);

        } else if (typeOfAction == TypeOfProgramActionEnum.DELETE) {
            programModel.deleteProgramFromDataBase(textFromFieldAsProgramName);

        } else if (typeOfAction == TypeOfProgramActionEnum.LOAD) {
            programModel.loadProgramFromDataBase(textFromFieldAsProgramName);

        } else if (typeOfAction == TypeOfProgramActionEnum.SAVE_AS) {
            programModel.saveProgramAsToDataBase(textFromFieldAsProgramName);

        } else if (typeOfAction == TypeOfProgramActionEnum.CHANGE_NAME) {
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


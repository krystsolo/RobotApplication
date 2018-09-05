package RobotApplication.controllers;

import RobotApplication.database.dao.TablesDao;
import RobotApplication.models.ProgramModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class ProgramLeftBorderController {

    private static final String CENTER_WINDOW_FXML = "/fxml/CenterWindow.fxml";
    private static final String PROGRAMS_VIEW_FXML = "/fxml/ProgramsView.fxml";
    private static final String operationTittle = "Zamyknaie programu";
    private static final String LOAD_ALERT_CONTENT = "Zapisz obecnie otwarty program przed zamnięciem.";

    private static MainController mainController;
    private static BooleanProperty programWindowOpened = new SimpleBooleanProperty(false);

    @FXML
    private Button clearListViewButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button nameChangeButton;
    @FXML
    private Label programNameLabel;
    @FXML
    private Button loadProgramButton;
    @FXML
    private Button saveProgramButton;
    @FXML
    private Button saveProgramAsButton;
    @FXML
    private Button createProgramButton;

    private ProgramModel programModel;

    @FXML
    public void initialize() {
        programModel = new ProgramModel();
        ProgramsViewController.setProgramLeftController(this);
        initBindings();
    }

    @FXML
    public void loadProgramOnAction() {
        ProgramsViewController.setTypeOfAction(TypeOfProgramActionEnum.LOAD);
        loadProgramsViewFxml();
    }

    @FXML
    public void saveProgramAsOnAction() {
        ProgramsViewController.setTypeOfAction(TypeOfProgramActionEnum.SAVE_AS);
        loadProgramsViewFxml();
    }

    @FXML
    public void deleteProgramOnAction() {
        ProgramsViewController.setTypeOfAction(TypeOfProgramActionEnum.DELETE);
        loadProgramsViewFxml();
    }

    @FXML
    public void saveProgramOnAction() {
        programModel.saveProgramToDataBase(TablesDao.getCurrentOpenedProgramName());
    }

    @FXML
    public void createProgramOnAction() {
        ProgramsViewController.setTypeOfAction(TypeOfProgramActionEnum.CREATE);
        loadProgramsViewFxml();
    }

    @FXML
    public void changeProgramNameOnAction() {
        ProgramsViewController.setTypeOfAction(TypeOfProgramActionEnum.CHANGE_NAME);
        loadProgramsViewFxml();
    }

    public void loadProgramsViewFxml() {
        mainController.setCenter(PROGRAMS_VIEW_FXML);
        programWindowOpened.set(true);
    }

    public void loadCenterWindowFxml() {
        mainController.setCenter(CENTER_WINDOW_FXML);
        programWindowOpened.set(false);
    }

    public static void setMainController(MainController mainController) {
        ProgramLeftBorderController.mainController = mainController;
    }

    public static BooleanProperty programWindowOpenedProperty() {
        return programWindowOpened;
    }

    @FXML
    public void clearListViewOnAction() {
        programModel.closeProgram();
        loadCenterWindowFxml();
    }

    private void initBindings() {
        BooleanProperty isInstructionWindowOpenned = ProgramRightBorderController.openedInstructionWindowProperty();
        this.programNameLabel.textProperty().bind(ProgramModel.openedProgramProperty());
        this.saveProgramButton.disableProperty().bind(
                ProgramModel.openedProgramProperty().isEmpty().or(
                        isInstructionWindowOpenned));
        createProgramButton.disableProperty().bind(isInstructionWindowOpenned);
        deleteButton.disableProperty().bind(isInstructionWindowOpenned);
        loadProgramButton.disableProperty().bind(isInstructionWindowOpenned);
        saveProgramAsButton.disableProperty().bind(isInstructionWindowOpenned);
        nameChangeButton.disableProperty().bind(isInstructionWindowOpenned);
        clearListViewButton.disableProperty().bind(isInstructionWindowOpenned.or(
                ProgramModel.openedProgramProperty().isEmpty()));
    }

}

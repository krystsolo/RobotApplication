package RobotApplication.controllers;

import RobotApplication.models.RobotModel;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ProgramRightBorderController {
    private static final String ADD_OR_EDIT_INSTRUCTION_WINDOW_FXML = "/fxml/AddOrEditInstructionWindow.fxml";
    private static final String PROGRAM_RIGHT_BORDER_FXML = "/fxml/ProgramRightBorder.fxml";

    private static MainController mainController;
    private static BooleanProperty openedInstructionWindow = new SimpleBooleanProperty(false);

    @FXML
    private Button addInstructionButton;
    @FXML
    private Button deleteInstructionButton;
    @FXML
    private Button editInstructionButton;

    @FXML
    public void initialize(){
        initBindings();
        AddOrEditInstructionWindowController.setProgramRightBorderController(this);
    }

    @FXML
    public void addInstructionOnAction( ) {
        AddOrEditInstructionWindowController.setTypeOfInstructionWindow(TypeOfInstructionWindowEnum.ADDING);
        loadAddOrEditWindow();
    }

    @FXML
    public void editInstructionOnAction( ) {
        AddOrEditInstructionWindowController.setTypeOfInstructionWindow(TypeOfInstructionWindowEnum.EDITING);
        loadAddOrEditWindow();
    }

    @FXML
    public void deleteInstructionOnAction( ) {
        RobotModel robotModel = new RobotModel();
        robotModel.deleteInstructionFromDataBase();
    }

    public void loadProgramRightBorder(){
        mainController.setRight(PROGRAM_RIGHT_BORDER_FXML);
        openedInstructionWindow.set(false);
    }

    public void loadAddOrEditWindow(){
        mainController.setRight(ADD_OR_EDIT_INSTRUCTION_WINDOW_FXML);
        openedInstructionWindow.set(true);
    }

    public static void setMainController(MainController mainController) {
        ProgramRightBorderController.mainController = mainController;
    }

    public static BooleanProperty openedInstructionWindowProperty() {
        return openedInstructionWindow;
    }

    private void initBindings() {
        BooleanProperty isProgramWindowOpened = ProgramLeftBorderController.programWindowOpenedProperty();
        this.addInstructionButton.disableProperty().bind(isProgramWindowOpened);
        this.deleteInstructionButton.disableProperty().bind(isProgramWindowOpened);
        this.editInstructionButton.disableProperty().bind(isProgramWindowOpened);
        BooleanBinding isAnyInstructionFromListViewChosen = RobotModel.chosenInstructionByClickProperty().isNull();
        editInstructionButton.disableProperty().bind(isAnyInstructionFromListViewChosen);
        deleteInstructionButton.disableProperty().bind(isAnyInstructionFromListViewChosen);
    }
}

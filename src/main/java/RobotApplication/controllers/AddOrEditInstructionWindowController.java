package RobotApplication.controllers;

import RobotApplication.models.*;
import RobotApplication.utils.ValidationData;
import javafx.beans.property.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.List;

public class AddOrEditInstructionWindowController {
    private static ProgramRightBorderController programRightBorderController;
    private static TypeOfInstructionWindowEnum typeOfInstructionWindow;

    @FXML
    private Button AddOrEditButton;
    @FXML
    private TextField parameter4Field;
    @FXML
    private Label parameter4Label;
    @FXML
    private TextField parameter3Field;
    @FXML
    private Label parameter3Label;
    @FXML
    private TextField parameter2Field;
    @FXML
    private Label parameter2Label;
    @FXML
    private TextField parameter1Field;
    @FXML
    private Label parameter1Label;
    @FXML
    private ComboBox typeOfActionComboBox;
    @FXML
    private TextField instructionNumberField;
    @FXML
    private Label addOrEditLabel;

    private RobotModel robotModel;

    @FXML
    public void initialize() {
        this.robotModel = new RobotModel();
        initLabelsText();
        initEditionProperty();
        initProperlyInputData();
        initVisibilityOfFields();
        initDisabilityOfConfirmButton();
    }

    @FXML
    public void backOnAction() {
        programRightBorderController.loadProgramRightBorder();
    }

    @FXML
    public void insertOrEditOnAction() {
        RobotAction robotAction = createRobotActionInstance();
        if(isRobotMoveParametersProper(robotAction)){
            if (typeOfInstructionWindow == TypeOfInstructionWindowEnum.ADDING) {
                robotModel.addInstructionToDataBase(robotAction);

            } else {
                robotModel.editInstructionToDataBase(robotAction);
            }
        }

    }

    public static void setTypeOfInstructionWindow(TypeOfInstructionWindowEnum typeOfInstructionWindow) {
        AddOrEditInstructionWindowController.typeOfInstructionWindow = typeOfInstructionWindow;
    }

    public static void setProgramRightBorderController(ProgramRightBorderController programRightBorderController) {
        AddOrEditInstructionWindowController.programRightBorderController = programRightBorderController;
    }

    private void initEditionProperty() {
        if (typeOfInstructionWindow == TypeOfInstructionWindowEnum.EDITING) {
            this.instructionNumberField.setDisable(true);
            RobotAction robotAction = RobotModel.getChosenInstructionByClick();
            List<Double> instructionParameters = robotAction.getParameters();
            this.instructionNumberField.setText(String.valueOf(robotAction.getInstructionNumber()));
            this.typeOfActionComboBox.getSelectionModel().select(robotAction.getTypeOfAction());
            if(robotAction.getTypeOfAction() == TypeOfAction.WAIT){
                this.instructionNumberField.setText(String.valueOf(instructionParameters.get(0)));
            } else if(robotAction.getTypeOfAction() == TypeOfAction.MJ || robotAction.getTypeOfAction() == TypeOfAction.MP){
                this.instructionNumberField.setText(String.valueOf(instructionParameters.get(0)));
                this.instructionNumberField.setText(String.valueOf(instructionParameters.get(1)));
                this.instructionNumberField.setText(String.valueOf(instructionParameters.get(2)));
                this.instructionNumberField.setText(String.valueOf(instructionParameters.get(3)));
            }
        }
    }

    private boolean isRobotMoveParametersProper(RobotAction robotAction) {
        if(robotAction.getTypeOfAction() == TypeOfAction.MJ){
            return ((RobotMove) robotAction).ifJointsAnglesCorrected();
        } else if(robotAction.getTypeOfAction() == TypeOfAction.MP){
            return ((RobotMove) robotAction).isTcpMoveParametersProper();
        } else {
            return true;
        }
    }

    private RobotAction createRobotActionInstance() {
        RobotAction robotAction = null;
        if (isChosenInComboBox(TypeOfAction.MP) || isChosenInComboBox(TypeOfAction.MJ)) {
            robotAction = new RobotMove(
                    Long.parseLong(instructionNumberField.getText()),
                    (TypeOfAction) typeOfActionComboBox.getSelectionModel().selectedItemProperty().get(),
                    Double.parseDouble(parameter1Field.getText()),
                    Double.parseDouble(parameter2Field.getText()),
                    Double.parseDouble(parameter3Field.getText()),
                    Double.parseDouble(parameter4Field.getText()));

        } else if (isChosenInComboBox(TypeOfAction.WAIT)) {
            robotAction = new RobotWait(
                    Long.parseLong(instructionNumberField.getText()),
                    (TypeOfAction) typeOfActionComboBox.getSelectionModel().selectedItemProperty().get(),
                    Long.parseLong(parameter1Field.getText()));

        } else if (isChosenInComboBox(TypeOfAction.GC) || isChosenInComboBox(TypeOfAction.GO)) {
            robotAction = new RobotGripper(
                    Long.parseLong(instructionNumberField.getText()),
                    (TypeOfAction) typeOfActionComboBox.getSelectionModel().selectedItemProperty().get());
        }
        return robotAction;
    }


    private void initProperlyInputData() {
        instructionNumberField.textProperty().addListener(ValidationData.isFieldContainingNumberFrom1To999());
        this.typeOfActionComboBox.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (isChosenInComboBox(TypeOfAction.MP)) {
                parameter1Field.textProperty().addListener(ValidationData.isFieldContainingDoubleNumber());
                parameter2Field.textProperty().addListener(ValidationData.isFieldContainingDoubleNumber());
                parameter3Field.textProperty().addListener(ValidationData.isFieldContainingDoubleNumber());
                parameter4Field.textProperty().addListener(ValidationData.isFieldContainingAnglesFrom0To180Degrees());

            } else if (isChosenInComboBox(TypeOfAction.MJ)) {
                parameter1Field.textProperty().addListener(ValidationData.isFieldContainingAnglesFrom0To180Degrees());
                parameter2Field.textProperty().addListener(ValidationData.isFieldContainingAnglesFrom0To180Degrees());
                parameter3Field.textProperty().addListener(ValidationData.isFieldContainingAnglesFrom0To180Degrees());
                parameter4Field.textProperty().addListener(ValidationData.isFieldContainingAnglesFrom0To180Degrees());

            } else if (isChosenInComboBox(TypeOfAction.WAIT)) {
                parameter1Field.textProperty().addListener(ValidationData.isFieldContainingLongNumber());
            }
        }));
    }

    private void initVisibilityOfFields() {
        this.typeOfActionComboBox.setItems(TypeOfAction.getObservableListOfTypesOfAction());
        ReadOnlyObjectProperty chosenTypeProperty = this.typeOfActionComboBox.getSelectionModel().selectedItemProperty();
        this.parameter1Field.visibleProperty().bind(
                chosenTypeProperty.isEqualTo(TypeOfAction.MJ).or(
                chosenTypeProperty.isEqualTo(TypeOfAction.MP)).or(
                        chosenTypeProperty.isEqualTo(TypeOfAction.WAIT)));

        this.parameter1Label.visibleProperty().bind(
                chosenTypeProperty.isEqualTo(TypeOfAction.MJ).or(
                chosenTypeProperty.isEqualTo(TypeOfAction.MP)).or(
                        chosenTypeProperty.isEqualTo(TypeOfAction.WAIT)));

        this.parameter2Field.visibleProperty().bind(
                chosenTypeProperty.isEqualTo(TypeOfAction.MJ).or(
                chosenTypeProperty.isEqualTo(TypeOfAction.MP)));

        this.parameter2Label.visibleProperty().bind(
                chosenTypeProperty.isEqualTo(TypeOfAction.MJ).or(
                chosenTypeProperty.isEqualTo(TypeOfAction.MP)));

        this.parameter3Field.visibleProperty().bind(
                chosenTypeProperty.isEqualTo(TypeOfAction.MJ).or(
                chosenTypeProperty.isEqualTo(TypeOfAction.MP)));

        this.parameter3Label.visibleProperty().bind(
                chosenTypeProperty.isEqualTo(TypeOfAction.MJ).or(
                chosenTypeProperty.isEqualTo(TypeOfAction.MP)));

        this.parameter4Field.visibleProperty().bind(
                chosenTypeProperty.isEqualTo(TypeOfAction.MJ).or(
                chosenTypeProperty.isEqualTo(TypeOfAction.MP)));

        this.parameter4Label.visibleProperty().bind(
                chosenTypeProperty.isEqualTo(TypeOfAction.MJ).or(
                chosenTypeProperty.isEqualTo(TypeOfAction.MP)));
    }

    private void initLabelsText() {
        this.addOrEditLabel.setText(typeOfInstructionWindow.toString());
        this.typeOfActionComboBox.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (isChosenInComboBox(TypeOfAction.WAIT)) {
                parameter1Label.setText("Czas czekania:");

            } else if (isChosenInComboBox(TypeOfAction.MJ)) {
                parameter1Label.setText("Obrót przegubu 1:");
                parameter2Label.setText("Obrót przegubu 2:");
                parameter3Label.setText("Obrót przegubu 3:");
                parameter4Label.setText("Obrót przegubu 4:");

            } else if (isChosenInComboBox(TypeOfAction.MP)) {
                parameter1Label.setText("X:");
                parameter2Label.setText("Y:");
                parameter3Label.setText("Z:");
                parameter4Label.setText("Alfa:");
            }
        }));
    }

    private void initDisabilityOfConfirmButton() {
        this.AddOrEditButton.disableProperty().bind(
                this.instructionNumberField.textProperty().isEmpty().or(
                this.parameter1Field.textProperty().isEmpty().and(this.parameter1Field.visibleProperty())).or(
                this.parameter2Field.textProperty().isEmpty().and(this.parameter2Field.visibleProperty())).or(
                this.parameter3Field.textProperty().isEmpty().and(this.parameter3Field.visibleProperty())).or(
                this.parameter4Field.textProperty().isEmpty().and(this.parameter4Field.visibleProperty())));
    }

    private boolean isChosenInComboBox(TypeOfAction typeOfAction) {
        return typeOfAction == typeOfActionComboBox.getSelectionModel().selectedItemProperty().get();
    }
}

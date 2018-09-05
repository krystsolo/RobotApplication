package RobotApplication.controllers;

import RobotApplication.models.*;
import RobotApplication.utils.ValidationData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SteerageRightBorderController {
    @FXML
    private TextField xTextField;
    @FXML
    private TextField yTextField;
    @FXML
    private TextField zTextField;
    @FXML
    private TextField tetaTextField;
    @FXML
    private Button tcpMoveButton;
    @FXML
    private TextField teta1TextField;
    @FXML
    private TextField teta2TextField;
    @FXML
    private TextField teta3TextField;
    @FXML
    private TextField teta4TextField;
    @FXML
    private Button jointMoveButton;

    @FXML
    public void initialize() {
        initBindings();
        SteerageLeftBorderController.setSteerageRightBorderController(this);
    }

    private void initBindings() {
        initDisabilityBindings();
        initFieldsValidation();
    }

    private void initFieldsValidation() {
        xTextField.textProperty().addListener(ValidationData.isFieldContainingDoubleNumber());
        yTextField.textProperty().addListener(ValidationData.isFieldContainingDoubleNumber());
        zTextField.textProperty().addListener(ValidationData.isFieldContainingDoubleNumber());
        tetaTextField.textProperty().addListener(ValidationData.isFieldContainingAnglesFrom0To180Degrees());

        teta1TextField.textProperty().addListener(ValidationData.isFieldContainingAnglesFrom0To180Degrees());
        teta2TextField.textProperty().addListener(ValidationData.isFieldContainingAnglesFrom0To180Degrees());
        teta3TextField.textProperty().addListener(ValidationData.isFieldContainingAnglesFrom0To180Degrees());
        teta4TextField.textProperty().addListener(ValidationData.isFieldContainingAnglesFrom0To180Degrees());
    }

    private void initDisabilityBindings() {
        tcpMoveButton.disableProperty().bind(
                xTextField.textProperty().isEmpty().or(
                        yTextField.textProperty().isEmpty().or(
                                zTextField.textProperty().isEmpty().or(
                                        tetaTextField.textProperty().isEmpty()))));
        jointMoveButton.disableProperty().bind(
                teta1TextField.textProperty().isEmpty().or(
                        teta2TextField.textProperty().isEmpty().or(
                                teta3TextField.textProperty().isEmpty().or(
                                        teta4TextField.textProperty().isEmpty()))));
    }

    @FXML
    public void tcpMoveOnAction() {
        RobotMove robotMove = new RobotMove(0,
                TypeOfAction.MP,
                Double.parseDouble(xTextField.getText()),
                Double.parseDouble(yTextField.getText()),
                Double.parseDouble(zTextField.getText()),
                Double.parseDouble(tetaTextField.getText()));
        if (robotMove.isTcpMoveParametersProper()) {
            robotMove.performOperation();
            RobotModel.setPreviousInstruction(robotMove);
        }
    }

    @FXML
    public void jointMoveOnAction() {
        RobotMove robotMove = new RobotMove(0,
                TypeOfAction.MJ,
                Double.parseDouble(teta1TextField.getText()),
                Double.parseDouble(teta2TextField.getText()),
                Double.parseDouble(teta3TextField.getText()),
                Double.parseDouble(teta4TextField.getText()));
        if (robotMove.ifJointsAnglesCorrected()) {
            robotMove.performOperation();
            RobotModel.setPreviousInstruction(robotMove);
        }
    }

    public void setFieldsWithPreviousInstructionParameters() {
        RobotMove robotMove = (RobotMove) RobotModel.getPreviousInstruction();
        this.xTextField.setText(String.valueOf(robotMove.getxPosition()));
        this.yTextField.setText(String.valueOf(robotMove.getyPosition()));
        this.zTextField.setText(String.valueOf(robotMove.getzPosition()));
        this.tetaTextField.setText(String.valueOf(robotMove.getTetaPosition()));
        this.teta1TextField.setText(String.valueOf(robotMove.getTeta1Position()));
        this.teta2TextField.setText(String.valueOf(robotMove.getTeta2Position()));
        this.teta3TextField.setText(String.valueOf(robotMove.getTeta3Position()));
        this.teta4TextField.setText(String.valueOf(robotMove.getTeta4Position()));
    }

}

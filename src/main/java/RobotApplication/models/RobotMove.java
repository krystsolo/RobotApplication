package RobotApplication.models;


import RobotApplication.utils.DialogsUtils;

import java.util.Arrays;
import java.util.List;

public class RobotMove extends RobotAction {
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private double tetaPosition;
    private double teta1Position;
    private double teta2Position;
    private double teta3Position;
    private double teta4Position;

    public RobotMove(long instructionNumber, TypeOfAction typeOfAction, double position1, double position2, double position3, double position4){
        super(instructionNumber, typeOfAction);
        if(getTypeOfAction() == TypeOfAction.MP){
            this.xPosition = position1;
            this.yPosition = position2;
            this.zPosition = position3;
            this.tetaPosition = position4;
        }
        else if (getTypeOfAction() == TypeOfAction.MJ){
            this.teta1Position = position1;
            this.teta2Position = position2;
            this.teta3Position = position3;
            this.teta4Position = position4;
        }
    }

    @Override
    public void performOperation(){
        System.out.println("wykonywanie ruchu");
    }

    @Override
    public String toString() {
       if(getTypeOfAction() == TypeOfAction.MJ) {
           return getInstructionNumber() + " " + getTypeOfAction().toString() + " " + teta1Position + ", " + teta2Position + ", " + teta3Position + ", " + teta4Position;
       }
       else{
           return getInstructionNumber() + " " + getTypeOfAction().toString() + " " + xPosition + ", " + yPosition + ", " + zPosition + ", " + tetaPosition;
       }
    }

    public boolean isTcpMoveParametersProper(){
        if(countTcpPosition()){
            return true;
        } else{
            DialogsUtils.errorDialog("Nie można osiągnąc tego punktu");
            return false;
        }
    }

    private boolean countTcpPosition(){
        // TODO: 15.08.2018 implementacja
        return true;
    }

    private boolean ifValueBetweenLimits(double value, int downBorder, int upBorder){
        return (value >= downBorder) && (value <= upBorder);
    }

    public boolean ifJointsAnglesCorrected(){
        return ifValueBetweenLimits(teta1Position, 0, 180) &&
                ifValueBetweenLimits(teta2Position, 0, 180) &&
                ifValueBetweenLimits(teta3Position, 0, 180) &&
                ifValueBetweenLimits(teta4Position, 0, 180);
    }

    public void countJointsPosition(){
        // TODO: 15.08.2018 implementacja

    }

    @Override
    public List<Double> getParameters() {
        if(getTypeOfAction() == TypeOfAction.MJ) {
            return Arrays.asList(teta1Position, teta2Position, teta3Position, teta4Position);
        } else{
            return Arrays.asList(xPosition, yPosition, zPosition, tetaPosition);
        }
    }

    public double getxPosition() {
        return xPosition;
    }

    public void setxPosition(double xPosition) {
        this.xPosition = xPosition;
    }

    public double getyPosition() {
        return yPosition;
    }

    public void setyPosition(double yPosition) {
        this.yPosition = yPosition;
    }

    public double getzPosition() {
        return zPosition;
    }

    public void setzPosition(double zPosition) {
        this.zPosition = zPosition;
    }

    public double getTetaPosition() {
        return tetaPosition;
    }

    public void setTetaPosition(double tetaPosition) {
        this.tetaPosition = tetaPosition;
    }

    public double getTeta1Position() {
        return teta1Position;
    }

    public void setTeta1Position(double teta1Position) {
        this.teta1Position = teta1Position;
    }

    public double getTeta2Position() {
        return teta2Position;
    }

    public void setTeta2Position(double teta2Position) {
        this.teta2Position = teta2Position;
    }

    public double getTeta3Position() {
        return teta3Position;
    }

    public void setTeta3Position(double teta3Position) {
        this.teta3Position = teta3Position;
    }

    public double getTeta4Position() {
        return teta4Position;
    }

    public void setTeta4Position(double teta4Position) {
        this.teta4Position = teta4Position;
    }
}

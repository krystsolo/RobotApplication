package RobotApplication.models;

import java.util.List;

public abstract class RobotAction {

    private long instructionNumber;
    private TypeOfAction typeOfAction;

    public RobotAction(long instructionNumber, TypeOfAction typeOfAction) {
        this.instructionNumber = instructionNumber;
        this.typeOfAction = typeOfAction;
    }

    public TypeOfAction getTypeOfAction() {
        return typeOfAction;
    }

    public long getInstructionNumber() {
        return instructionNumber;
    }

    public abstract String toString();

    public abstract void performOperation();

    public abstract List<Double> getParameters();

    public void setInstructionNumber(long instructionNumber) {
        this.instructionNumber = instructionNumber;
    }

}


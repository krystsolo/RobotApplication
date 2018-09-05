package RobotApplication.models;

import java.util.Collections;
import java.util.List;

public class RobotWait extends RobotAction {
    private long waitTime;

    public RobotWait(long instructionNumber,TypeOfAction typeOfAction, long waitTime){
        super(instructionNumber, typeOfAction);
        this.waitTime = waitTime;
    }

    @Override
    public void performOperation() {
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return getInstructionNumber() + " " + getTypeOfAction() + " " + waitTime;
    }

    @Override
    public List<Double> getParameters() {
        return Collections.singletonList((double) waitTime);
    }
}

package RobotApplication.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Collections;
import java.util.List;

public class RobotGripper extends RobotAction {

    private static StringProperty currentGripperPosition = new SimpleStringProperty(null);

    public RobotGripper(long instructionNumber, TypeOfAction typeOfAction){
        super(instructionNumber, typeOfAction);
    }
    @Override
    public void performOperation() {
        currentGripperPosition.set( super.getTypeOfAction().name());
        if(currentGripperPosition.get().equals(TypeOfAction.GC.name())){
            closeGripper();
        } else{
            openGripper();
        }

    }

    private void openGripper() {
        // TODO: 15.08.2018 implementacja

    }

    private void closeGripper() {
        // TODO: 15.08.2018 implementacja

    }

    @Override
    public String toString() {
        return getInstructionNumber() + " " + getTypeOfAction().toString();
    }

    @Override
    public List<Double> getParameters() {
        return Collections.emptyList();
    }

    public static String getCurrentGripperPosition() {
        return currentGripperPosition.get();
    }

    public static StringProperty currentGripperPositionProperty() {
        return currentGripperPosition;
    }


}

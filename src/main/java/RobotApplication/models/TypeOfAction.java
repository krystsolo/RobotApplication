package RobotApplication.models;

import javafx.beans.property.ListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.List;

public enum TypeOfAction {
    MJ("MJ"), MP("MP"), GC("GRIPPER CLOSE"), GO("GRIPPER OPEN"), WAIT("WAIT");

    private String typeOfActionContent;

    private TypeOfAction(String typeOfActionContent){
        this.typeOfActionContent = typeOfActionContent;
    }

    public String getContent(){
        return typeOfActionContent;
    }

    public static List<TypeOfAction> getListOfTypesOfAction(){
        return Arrays.asList(TypeOfAction.values());
    }

    public static ObservableList<TypeOfAction> getObservableListOfTypesOfAction(){
        return FXCollections.observableList(getListOfTypesOfAction());
    }
}

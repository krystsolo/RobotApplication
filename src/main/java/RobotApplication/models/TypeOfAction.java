package RobotApplication.models;

import javafx.beans.property.ListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.List;

public enum TypeOfAction {
    MJ{
        @Override
        public String toString() {
            return "MJ";
        }
    },
    MP{
        @Override
        public String toString() {
            return "MP";
        }
    },
    GC {
        @Override
        public String toString() {
            return "GRIPPER CLOSE";
        }
    },
    GO {
        @Override
        public String toString() {
            return "GRIPPER OPEN";
        }
    },
    WAIT{
        @Override
        public String toString() {
            return "WAIT";
        }
    };

    public static List<TypeOfAction> getListOfTypesOfAction(){
        return Arrays.asList(TypeOfAction.values());
    }

    public static ObservableList<TypeOfAction> getObservableListOfTypesOfAction(){
        return FXCollections.observableList(getListOfTypesOfAction());
    }
}

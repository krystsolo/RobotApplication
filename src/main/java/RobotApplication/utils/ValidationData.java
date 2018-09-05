package RobotApplication.utils;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;

public class ValidationData {
    public static ChangeListener isFieldContainingNumberFrom1To999(){
        return (ChangeListener<String>) (observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
                ((StringProperty) observable).set(oldValue);
            else{
                if(newValue.length() > 3){
                    ((StringProperty) observable).set(oldValue);
                }
            }
        };
    }

    public static ChangeListener isFieldContainingDoubleNumber(){
        return (ChangeListener<String>) (observable, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d{0,3}(\\.{1}\\d{0,3})?+"))
                ((StringProperty) observable).set(oldValue);
            };
    }

    public static ChangeListener isFieldContainingLongNumber(){
        return (ChangeListener<String>) (observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
                ((StringProperty) observable).set(oldValue);
            else{
                if(newValue.length() > 8){
                    ((StringProperty) observable).set(oldValue);
                }
            }
        };
    }

    public static ChangeListener isFieldContainingAnglesFrom0To180Degrees(){
        return (ChangeListener<String>) (observable, oldValue, newValue) -> {
            if(!newValue.isEmpty()) {
                if (!newValue.matches("\\d{0,3}(\\.{1}\\d{0,3})?+")) {
                    ((StringProperty) observable).set(oldValue);
                } else {
                    double angle = Double.parseDouble(newValue);
                    if (angle > 180) {
                        ((StringProperty) observable).set(oldValue);
                    }
                }
            }
        };
    }
}


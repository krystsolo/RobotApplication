package RobotApplication.utils;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class DialogsUtils  {

    public static void helpDialog(){
        Alert helpAlert = new Alert(Alert.AlertType.INFORMATION);
        helpAlert.setTitle("Pomoc");
        helpAlert.setContentText("W budowie");
        helpAlert.showAndWait();
    }

    public static Optional<ButtonType> confirmNewProgramDialog(String title, String contentText){
        Alert loadNewProgramALert = new Alert(Alert.AlertType.CONFIRMATION);
        loadNewProgramALert.setTitle(title);
        loadNewProgramALert.setContentText(contentText);
        return loadNewProgramALert.showAndWait();
    }

    public static void errorDialog(String error){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Błąd!");
        errorAlert.setHeaderText("Wystąpił niespodziewany błąd:");

        TextArea textArea = new TextArea(error);
        errorAlert.getDialogPane().setContent(textArea);
        errorAlert.showAndWait();
    }

    public static Optional addInstructionNumberDialog(){
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Zapisywanie instrukcji");
        dialog.setHeaderText("Wybranie istniejącego numeru instrukcji spowoduje jej nadpisanie");

        ButtonType loginButtonType = new ButtonType("Zapisz", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField instructionNumberTextField = new TextField();
        instructionNumberTextField.setPromptText("Number instrukcji");

        grid.add(new Label("Numer instrukcji:"), 0, 0);
        grid.add(instructionNumberTextField, 1, 0);

        Node saveButton = dialog.getDialogPane().lookupButton(loginButtonType);
        saveButton.disableProperty().bind(instructionNumberTextField.textProperty().isEmpty());
        instructionNumberTextField.textProperty().addListener(ValidationData.isFieldContainingNumberFrom1To999());

        dialog.getDialogPane().setContent(grid);
        Platform.runLater(instructionNumberTextField::requestFocus);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return instructionNumberTextField.getText();
            }
            return null;
        });

        return dialog.showAndWait();
    }



}

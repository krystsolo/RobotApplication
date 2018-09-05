package RobotApplication.models;

import RobotApplication.controllers.CenterWindowController;
import RobotApplication.database.DBUtils.JdbcConnection;
import RobotApplication.database.dao.RobotDao;
import RobotApplication.database.dao.TablesDao;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class RobotModel {

    private static ObservableList<RobotAction> operationsList = FXCollections.observableArrayList();
    private static ObjectProperty<RobotAction> previousInstruction = new SimpleObjectProperty<>();
    private static ObjectProperty<RobotAction> chosenInstructionByClick = new SimpleObjectProperty<>();
    private static CenterWindowController centerWindowController;

    public void initOperationList(){
        RobotDao robotDao = new RobotDao();
        List<RobotAction> instructionForActualProgram = robotDao.selectAllRobotActions(TablesDao.getTemporaryAssistantProgram());
        operationsList.clear();
        operationsList.addAll(instructionForActualProgram);
        centerWindowController.refreshInstructionListView(operationsList);
    }

    public void editInstructionToDataBase(RobotAction robotAction) {
        RobotDao robotDao = new RobotDao();
        robotDao.updateRobotAction(robotAction);
        initOperationList();
    }

    public void addInstructionToDataBase(RobotAction robotAction) {
        RobotDao robotDao = new RobotDao();
        if(isAlreadyExistInstructionNumberInDataBase(robotAction)){
            robotDao.updateRobotAction(robotAction);
        } else{
            robotDao.insertRobotAction(robotAction);
        }
        initOperationList();
    }

    public boolean isAlreadyExistInstructionNumberInDataBase(RobotAction robotAction){
        RobotDao robotDao = new RobotDao();
        List<RobotAction> actionList = robotDao.selectAllRobotActions(TablesDao.getTemporaryAssistantProgram());

        return actionList.stream().anyMatch(s -> s.getInstructionNumber() == robotAction.getInstructionNumber());
    }

    public void deleteInstructionFromDataBase(){
        RobotDao robotDao = new RobotDao();
        robotDao.deleteAction(chosenInstructionByClick.get().getInstructionNumber());
        initOperationList();
    }

    public RobotAction selectInstructionFromDataBaseById(Long instructionNumber){
        RobotDao robotDao = new RobotDao();
        return robotDao.selectRobotActionByInstructionNumber(TablesDao.getTemporaryAssistantProgram(), instructionNumber);
    }

    public static ObservableList<RobotAction> getOperationsList() {
        return operationsList;
    }

    public static RobotAction getChosenInstructionByClick() {
        return chosenInstructionByClick.get();
    }

    public static void setCenterWindowController(CenterWindowController centerWindowController) {
        RobotModel.centerWindowController = centerWindowController;
    }

    public static ObjectProperty<RobotAction> chosenInstructionByClickProperty() {
        return chosenInstructionByClick;
    }

    public static RobotAction getPreviousInstruction() {
        return previousInstruction.get();
    }

    public static ObjectProperty<RobotAction> previousInstructionProperty() {
        return previousInstruction;
    }

    public static void setPreviousInstruction(RobotAction previousInstruction) {
        RobotModel.previousInstruction.set(previousInstruction);
    }

}

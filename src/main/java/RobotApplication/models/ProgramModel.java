package RobotApplication.models;

import RobotApplication.database.dao.TablesDao;
import RobotApplication.utils.DialogsUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProgramModel {

    private static final String SAVING_AS_ALERT_CONTENT = "Obecnie jest już program o tej nazwie. Utworzenie nowego programu o tej samej nazwie spowoduje utratę wcześniej utworzonego programu.";
    private static final String LOAD_ALERT_CONTENT = "Obecnie jest już program o tej nazwie. Utworzenie nowego programu o tej samej nazwie spowoduje utratę wcześniej utworzonego programu.";
    private static final String REMOVE_ALERT_CONTENT = "Obecnie jest już program o tej nazwie. Utworzenie nowego programu o tej samej nazwie spowoduje utratę wcześniej utworzonego programu.";
    private static final String CREATE_ALERT_CONTENT = "Obecnie jest już program o tej nazwie. Utworzenie nowego programu o tej samej nazwie spowoduje utratę wcześniej utworzonego programu.";

    private ObservableList<String> programList = FXCollections.observableArrayList();
    private static StringProperty openedProgram = new SimpleStringProperty();

    private TablesDao tablesDao = new TablesDao();


    public void createProgramInDataBase(String nameNewProgram) {

        boolean isProgramNameAlreadyExist = ifProgramNameAlreadyExist(nameNewProgram);
        if (isProgramNameAlreadyExist) {
            Optional<ButtonType> result = DialogsUtils.confirmNewProgramDialog("Tworzenie nowego programu", CREATE_ALERT_CONTENT);
            if (result.get() == ButtonType.OK) {
                recreateProgram(nameNewProgram);
            }
        } else {
            tablesDao.createProgram(nameNewProgram);
            TablesDao.setCurrentOpenedProgramName(nameNewProgram);
            initProgramsList();
        }
    }

    public void recreateProgram(String programName) {
        tablesDao.dropProgram(programName);
        tablesDao.createProgram(programName);
    }

    public void initProgramsList() {
        tablesDao.createProgram(getTemporaryProgramName());
        List <String> listOfPrograms = tablesDao.getProgramsNames();
        List<String> listFiltered = listOfPrograms.stream().filter(s -> ! s.equals(getTemporaryProgramName())).collect(Collectors.toList());
        this.programList.clear();
        this.programList.addAll(listFiltered);
        openedProgram.setValue(TablesDao.getCurrentOpenedProgramName());
    }

    public void deleteProgramFromDataBase(String deletedProgramName){

        boolean isProgramNameAlreadyExist = ifProgramNameAlreadyExist(deletedProgramName);
// TODO: 03.09.2018 uzyc try catch z przechyceniem w kontrolerze
        if (isProgramNameAlreadyExist) {
            if (DialogsUtils.confirmNewProgramDialog("Usuwanie programu", REMOVE_ALERT_CONTENT).get() == ButtonType.OK) {
                tablesDao.dropProgram(deletedProgramName);
                TablesDao.setCurrentOpenedProgramName(null);
                initProgramsList();
            }
        } else {
            DialogsUtils.errorDialog("Nie ma programu o takiej nazwie.");
        }
    }

    public void saveProgramToDataBase(String programName) {
            tablesDao.dropProgram(programName);
            tablesDao.copyOfSelectedProgram(getTemporaryProgramName(), TablesDao.getCurrentOpenedProgramName());
    }

    public boolean ifProgramNameAlreadyExist(String programName){
        return tablesDao.getProgramsNames().contains(programName);
    }

    public void saveProgramAsToDataBase(String programName) {
        boolean isProgramNameAlreadyExist = ifProgramNameAlreadyExist(programName);

        if (!isProgramNameAlreadyExist || DialogsUtils.confirmNewProgramDialog("Zapisz jako", SAVING_AS_ALERT_CONTENT).get() == ButtonType.OK) {
            if(isProgramNameAlreadyExist){
                saveProgramToDataBase(programName);
            } else{
                tablesDao.dropProgram(programName);
                tablesDao.copyOfSelectedProgram(getTemporaryProgramName(), programName);
                TablesDao.setCurrentOpenedProgramName(programName);
                initProgramsList();
            }

        }
    }

    public void loadProgramFromDataBase(String programName) {

        boolean isListViewNotEmpty = isProgramNotEmpty(getTemporaryProgramName());
        boolean isProgramNameAlreadyExist = ifProgramNameAlreadyExist(programName);
        if(isProgramNameAlreadyExist)
        {
            if ( isListViewNotEmpty || DialogsUtils.confirmNewProgramDialog("Wczytaj program:", LOAD_ALERT_CONTENT).get() == ButtonType.OK) {
                tablesDao.dropProgram(getTemporaryProgramName());
                tablesDao.copyOfSelectedProgram(programName, getTemporaryProgramName());
                TablesDao.setCurrentOpenedProgramName(programName);
                initProgramsList();
            }
        } else{
            DialogsUtils.errorDialog("Program o nazwie '" + programName + "' nie istnieje.");
        }
    }

    public void changeProgramName(String oldName, String newName) {

        boolean ifProgramNameAlreadyExist = ifProgramNameAlreadyExist(newName);
        if (! ifProgramNameAlreadyExist) {
            tablesDao.changeProgramName(oldName, newName);
            initProgramsList();
            if(oldName.equals(TablesDao.getCurrentOpenedProgramName())){
                TablesDao.setCurrentOpenedProgramName(newName);
            }
        } else {
            DialogsUtils.errorDialog("Istnieje już program o tej nazwie.");
        }
    }

    public boolean isProgramNotEmpty(String programName){
        return tablesDao.isTableNotEmpty(programName);
    }

    // TODO: 05.09.2018
    public void closeProgram(){
        boolean isListViewNotEmpty = isProgramNotEmpty(getTemporaryProgramName());
        if ( !isListViewNotEmpty || DialogsUtils.confirmNewProgramDialog("Czyszczenie obecnego okna", LOAD_ALERT_CONTENT).get() == ButtonType.OK) {
            tablesDao.dropProgram(getTemporaryProgramName());
            TablesDao.setCurrentOpenedProgramName(null);
        }
        initProgramsList();
    }

    public String getTemporaryProgramName(){
        return TablesDao.getTemporaryAssistantProgram();
    }

    public static StringProperty openedProgramProperty() {
        return openedProgram;
    }

    public ObservableList<String> getProgramList() {
        return programList;
    }
}

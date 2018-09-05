package RobotApplication.database.dao;

import RobotApplication.database.DBUtils.JdbcConnection;
import RobotApplication.utils.DialogsUtils;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TablesDao {
    private final static String temporaryAssistantProgram = "Temp";
    private static String currentOpenedProgramName = null;

    public static String getTemporaryAssistantProgram() {
        return temporaryAssistantProgram;
    }

    public static String getCurrentOpenedProgramName() {
        return currentOpenedProgramName;
    }

    public static void setCurrentOpenedProgramName(String currentOpenedProgramName) {
        TablesDao.currentOpenedProgramName = currentOpenedProgramName;
    }

    public void createProgram(String programName) {
        String createTable = String.format("CREATE TABLE IF NOT EXISTS '%s' " +
                "( instructionNumber INTEGER PRIMARY KEY, " +
                "typeOfRobotAction varchar(5), " +
                "parameter1 DOUBLE, " +
                "parameter2 DOUBLE, " +
                "parameter3 DOUBLE, " +
                "parameter4 DOUBLE );",
                programName );
        tableOperation(createTable);
    }

    public void dropProgram(String programName) {
        String dropProgram = String.format("DROP TABLE IF EXISTS '%s';", programName);
        tableOperation(dropProgram);
    }

    public List<String> getProgramsNames() {
        List<String> listOfPrograms = new ArrayList<>();

        String sql = "SELECT tbl_name FROM sqlite_master WHERE type = 'table';";
        try(Connection connection = JdbcConnection.getConnection();
            Statement  statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                listOfPrograms.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        return listOfPrograms;
    }

    public void changeProgramName(String oldName, String newName) {
        String changeName = String.format("ALTER TABLE '%s' RENAME TO '%s';", oldName, newName);
        tableOperation(changeName);
    }

    public void copyOfSelectedProgram(String copiedProgramName, String programNameWhereIsCopied) {
        String createAs = String.format("CREATE TABLE '%s' AS SELECT * FROM '%s';", programNameWhereIsCopied, copiedProgramName);
        tableOperation(createAs);
    }

    public boolean isTableNotEmpty(String programName) {
        String sqlStat = String.format("SELECT EXISTS (SELECT 1 FROM '%s');", programName);
        try(Connection connection = JdbcConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStat))
        {
            return resultSet.next();
        } catch (SQLException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        return false;
    }

    private void tableOperation(String operationContent) {
        try(Connection connection = JdbcConnection.getConnection();
            Statement statement = connection.createStatement()) {
            statement.execute(operationContent);
        } catch (SQLException e) {
            e.printStackTrace();
            DialogsUtils.errorDialog(e.getMessage());
        }
    }

}

package RobotApplication.database.dao;

import RobotApplication.database.DBUtils.JdbcConnection;
import RobotApplication.models.*;
import RobotApplication.utils.DialogsUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RobotDao {

    public void insertRobotAction(RobotAction robotAction) {
        List<Double> listOfParameters = retrieveParameters(robotAction);

        String insertSQL = String.format("INSERT INTO %s VALUES (%d, '%s', %f, %f, %f, %f);",
                TablesDao.getTemporaryAssistantProgram(), robotAction.getInstructionNumber(), robotAction.getTypeOfAction().name(),
                listOfParameters.get(0), listOfParameters.get(1), listOfParameters.get(2), listOfParameters.get(3));

        databaseOperation(insertSQL);
    }

    public RobotAction selectRobotActionByInstructionNumber(String programName, Long instructionNumber) {
        String selectAll = "SELECT * FROM " + programName + " WHERE instructionNumber = " + instructionNumber + " ;";

        try (Connection connection = JdbcConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectAll)) {

            TypeOfAction actionType;
            double parameter1, parameter2, parameter3, parameter4;
            while (resultSet.next()) {
                actionType = (TypeOfAction.valueOf(resultSet.getString(2)));
                parameter1 = resultSet.getDouble(3);
                parameter2 = resultSet.getDouble(4);
                parameter3 = resultSet.getDouble(5);
                parameter4 = resultSet.getDouble(6);

                if (actionType.equals(TypeOfAction.MJ) || actionType.equals(TypeOfAction.MP)) {
                    return new RobotMove(instructionNumber, actionType, parameter1, parameter2, parameter3, parameter4);
                } else if (actionType.equals(TypeOfAction.GO) || actionType.equals(TypeOfAction.GC)) {
                    return new RobotGripper(instructionNumber, actionType);
                } else if (actionType.equals(TypeOfAction.WAIT)) {
                    return new RobotWait(instructionNumber, actionType, (long) parameter1);
                }
            }
        } catch (SQLException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        return null;
    }

    public List<RobotAction> selectAllRobotActions(String programName) {
        List<RobotAction> robotActions = new ArrayList<RobotAction>();
        String selectAll = "SELECT * FROM " + programName + " ORDER BY instructionNumber;";
        try (Connection connection = JdbcConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectAll)) {

            long instructionNumber;
            TypeOfAction actionType;
            double parameter1, parameter2, parameter3, parameter4;
            while (resultSet.next()) {
                instructionNumber = resultSet.getLong(1);
                actionType = (TypeOfAction.valueOf(resultSet.getString(2)));
                parameter1 = resultSet.getDouble(3);
                parameter2 = resultSet.getDouble(4);
                parameter3 = resultSet.getDouble(5);
                parameter4 = resultSet.getDouble(6);

                if (actionType.equals(TypeOfAction.MJ) || actionType.equals(TypeOfAction.MP)) {
                    robotActions.add(new RobotMove(instructionNumber, actionType, parameter1, parameter2, parameter3, parameter4));
                } else if (actionType.equals(TypeOfAction.GO) || actionType.equals(TypeOfAction.GC)) {
                    robotActions.add(new RobotGripper(instructionNumber, actionType));
                } else if (actionType.equals(TypeOfAction.WAIT)) {
                    robotActions.add(new RobotWait(instructionNumber, actionType, (long) parameter1));
                }
            }
        } catch (SQLException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        return robotActions;
    }

    public void updateRobotAction(RobotAction robotAction) {
        List<Double> listOfParameters = retrieveParameters(robotAction);

        String updateSql = String.format("UPDATE %s SET typeOfRobotAction = '%s'" +
                        ", parameter1 =  %f, parameter2 = %f" +
                        ", parameter3 = %f, parameter4 =  %f " +
                        "WHERE instructionNumber = %d;",
                TablesDao.getTemporaryAssistantProgram(), robotAction.getTypeOfAction().name(),
                listOfParameters.get(0), listOfParameters.get(1),
                listOfParameters.get(2), listOfParameters.get(3),
                robotAction.getInstructionNumber());

        databaseOperation(updateSql);
    }

    public void deleteAction(long instructionNumber) {
        String deleteSQL = "DELETE FROM " + TablesDao.getTemporaryAssistantProgram() +
                " WHERE instructionNumber = " + instructionNumber + ";";

        databaseOperation(deleteSQL);
    }

    private void databaseOperation(String sqlQuery) {
        try (Connection connection = JdbcConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }

    private List<Double> retrieveParameters(RobotAction robotAction) {
        List<Double> listOfParameters = Arrays.asList(0.0, 0.0, 0.0, 0.0);
        List<Double> retrievedParameters = robotAction.getParameters();
        for (int index = 0; index < retrievedParameters.size(); index++) {
            listOfParameters.set(index, retrievedParameters.get(index));
        }
        return listOfParameters;
    }
}


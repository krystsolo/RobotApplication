package RobotApplication.database.DBUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class JdbcConnection {
    private static final String DRIVER = "org.sqlite.JDBC";
    private static final String DB_URL = "jdbc:sqlite:C:\\Users\\Krystian\\IdeaProjects\\RobotApplication\\robotDB";

    public static Connection getConnection() {
        loadDatabaseDriver();

        return makeConnection();
    }

    private static Connection makeConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static void loadDatabaseDriver() {
        try {
            Class.forName(JdbcConnection.DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}

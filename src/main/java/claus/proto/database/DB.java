package claus.proto.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Stack;


public class DB
{

    private static Connection connection;

    private static final Stack<Savepoint> savePoints = new Stack<>();

    public static void beginTransaction() throws SQLException
    {
        if (connection != null)
            return;
        // Get database credentials from DatabaseConfig class
        var jdbcUrl = DatabaseConfig.getDbUrl();
        var user = DatabaseConfig.getDbUsername();
        var password = DatabaseConfig.getDbPassword();

        // Open a connection
        connection = DriverManager.getConnection(jdbcUrl, user, password);
        connection.setAutoCommit(false);
        connection.commit();
        savePoints.add(connection.setSavepoint());
    }

    public static void endTransaction() throws SQLException
    {
        connection.commit();
        connection.close();
        connection = null;
    }

    public static void setSavePoint() throws SQLException
    {
        savePoints.add(connection.setSavepoint());
    }

    public static void rollback() throws SQLException
    {
        if (savePoints.isEmpty()) {
            System.out.println("No savepoint to rollback to");
            return;
        }
        if (connection == null) {
            System.out.println("No transaction in progress");
            return;
        }

        connection.rollback(savePoints.pop());

    }

    public static Connection getConnection()
    {
        if (connection == null)
            throw new RuntimeException("No transaction in progress");

        return connection;
    }
}


package claus.backend.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;


// make connections pooled
public class DB {

    private static final int size = 50;

    private static final ArrayList<PoolConnection> connectionPool = new ArrayList<>();

    private static Connection addConnection() throws SQLException {

        if (connectionPool.size() == size) {
            throw new RuntimeException("Connection pool full");
        }
        // Get database credentials from DatabaseConfig class
        var jdbcUrl = DatabaseConfig.getDbUrl();
        var user = DatabaseConfig.getDbUsername();
        var password = DatabaseConfig.getDbPassword();

        Connection connection;

        // Open a connection
        connection = DriverManager.getConnection(jdbcUrl, user, password);

        // connections are only added to be used immediately
        connectionPool.add(new PoolConnection(connection, true));
        return connection;
    }

    private static void removeConnection(PoolConnection con) throws SQLException {
        if (!con.connection.isClosed()) {
            con.connection.setAutoCommit(false);
            con.connection.rollback();
            con.connection.close();
        }

        connectionPool.remove(con);
    }

    /**
     * Closes all connections that are not currently in use.
     *
     * @throws SQLException if database access error occurs
     */
    public static void cleanPool() throws SQLException
    {
        // remove any connections not in use
        for (int i = 0; i < connectionPool.size(); i++) {
            PoolConnection con = connectionPool.get(i);
            if (!con.inUse) {
                removeConnection(con);
                i--;
            }
        }
    }

    /**
     * Closes and removes all connections to database. Generally not advisable
     * @throws SQLException if a database access error occurs
     */
    public static void clearPool() throws SQLException
    {
        for (int i = 0; i < connectionPool.size(); i++) {
            PoolConnection con = connectionPool.get(i);
            removeConnection(con);
            i--;
        }
    }

    /**
     * Get database connection that is not currently in use
     * @return a connection to the database
     * @throws SQLException if a database access error occurs or the url is null
     */
    public static Connection getConnection() throws SQLException
    {
        // get first connection not in use
        for (PoolConnection con : connectionPool) {
            if (!con.inUse) {
                con.inUse = true;
                return con.connection;
            }
        }
        // if none are in use, we add one
        return addConnection();
    }

    /**
     * Frees connection to be used by other processes
     * @param connection connection to be freed once the task at hand is completed
     * @throws RuntimeException if connection is not in the pool
     */
    public static void freeConnection(Connection connection) throws RuntimeException {
        PoolConnection con = new PoolConnection(connection, false);
        int i = connectionPool.indexOf(con);
        if (i == -1) {
            throw new RuntimeException("Trying to free a connection that is not in the pool");
        }
        connectionPool.get(i).inUse = false;
    }

    /**
     *
     * @return number of connections currently in pool
     */
    public static int poolSize() {
        return connectionPool.size();
    }

    /**
     *
     * @return number of in-use connections currently in pool
     */
    public static int activePoolSize() {
        int size = 0;
        for (var con : connectionPool) {
            if (con.inUse)
                size++;
        }

        return size;
    }

    /**
     *
     * @return number of not in-use connections currently in the pool
     */
    public static int inactivePoolSize() {
        return poolSize() - activePoolSize();
    }

}

class PoolConnection
{
    Connection connection;
    boolean inUse;

    PoolConnection(Connection con, boolean inUse) {
        this.connection = con;
        this.inUse = inUse;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof PoolConnection other))
            return false;
        return other.connection.equals(connection);
    }
}


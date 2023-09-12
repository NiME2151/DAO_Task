package kaufvertrag.dataLayer.dataAccessObjects.sqlite;

import kaufvertrag.exceptions.DaoException;

import java.sql.*;

public class ConnectionManager {

    private final String CLASSNAME = "ConnectionManager";
    private static final String CONNECTIONSTRING = "jdbc:sqlite:database.db";
    private static Connection existingConnection;
    private static boolean classLoaded = false;

    public static Connection getNewConnection() throws DaoException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(CONNECTIONSTRING);
            existingConnection = connection;
            classLoaded = true;
            return connection;
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        } 
        // warum das finally hier? 
        /*finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }*/
    }

    public static Connection getExistingConnection() {
        return existingConnection;
    }

    public static void close(ResultSet resultSet, Statement statement, Connection connection) throws DaoException {
        try {
            resultSet.close();
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        } finally {
            try {
                statement.close();
            } catch (Exception e) {
                throw new DaoException(e.getMessage());
            }

            try {
                connection.close();
            } catch (Exception e) {
                throw new DaoException(e.getMessage());
            }
            existingConnection = null;
            classLoaded = false;
        }
    }
}

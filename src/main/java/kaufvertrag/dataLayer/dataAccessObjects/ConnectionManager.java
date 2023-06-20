package kaufvertrag.dataLayer.dataAccessObjects;

import kaufvertrag.exceptions.DaoException;

import java.sql.*;

public class ConnectionManager {

    private static String CLASSNAME;
    private static String CONNECTIONSTRING;
    private static Connection existingConnection;
    private static boolean classLoaded;

    public Connection getNewConnection() {
        Connection conn = null;
        String url = "jdbc:sqlite:database.db";
        try {
            conn = DriverManager.getConnection(url);
            return conn;
        }
        catch (Exception e) {
            throw new DaoException(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static Connection getExistingConnection() {
        return existingConnection;
    }

    public void close(ResultSet resultSet, Statement statement, Connection connection) {
        // TODO
    }
}

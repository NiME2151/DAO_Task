package kaufvertrag.dataLayer.dataAccessObjects.sqlite;

import kaufvertrag.exceptions.DaoException;

import java.sql.*;

public class ConnectionManager {

    private final String CLASSNAME = "org.sqlite.JDBC";
    private static final String CONNECTIONSTRING = "jdbc:sqlite:C:../DAO_Task/database.db";
    private static Connection existingConnection;
    private static boolean classLoaded = false;

    private void loadClass() throws DaoException {
        if (classLoaded) return;
        try {
            Class.forName(CLASSNAME);
        } catch (ClassNotFoundException e) {
            throw new DaoException(e.getMessage());
        }
        classLoaded = true;
    }

    public Connection getNewConnection() throws DaoException {
        loadClass();

        try {
            existingConnection = DriverManager.getConnection(CONNECTIONSTRING);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }

        String createWareTableIfNotExists = """
                CREATE TABLE IF NOT EXISTS Ware(
                id INTEGER NOT NULL,
                bezeichnung VARCHAR(50),
                beschreibung VARCHAR(255),
                preis DOUBLE(255),
                maengel TEXT,
                besonderheiten TEXT
                );""";

        String createVertragspartnerTableIfNotExists = """
                CREATE TABLE IF NOT EXISTS Vertragspartner(
                ausweisNr VARCHAR(50) NOT NULL,
                vorname VARCHAR(50),
                nachname VARCHAR(50),
                strasse VARCHAR(50),
                hausNr VARCHAR(50),
                plz VARCHAR(50),
                ort VARCHAR(50)
                );""";

        try {
            existingConnection.createStatement().execute(createWareTableIfNotExists);
            existingConnection.createStatement().execute(createVertragspartnerTableIfNotExists);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return existingConnection;
    }

    public Connection getExistingConnection() {
        return existingConnection;
    }


    public void close() throws DaoException {
        try {
            existingConnection.close();
            classLoaded = false;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    public void close(Statement statement) throws DaoException {
        close();
        try {
            statement.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }


    public void close(ResultSet resultSet, Statement statement) throws DaoException {
        close(statement);
        try {
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }
}

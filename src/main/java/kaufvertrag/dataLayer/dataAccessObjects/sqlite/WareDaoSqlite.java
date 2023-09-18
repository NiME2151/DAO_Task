package kaufvertrag.dataLayer.dataAccessObjects.sqlite;

import kaufvertrag.dataLayer.businessObjects.Ware;
import kaufvertrag.dataLayer.dataAccessObjects.IDao;
import kaufvertrag.exceptions.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WareDaoSqlite implements IDao<Ware, Long> {

    ConnectionManager connectionManager = new ConnectionManager();

    @Override
    public Ware create() {
        return (new Ware());
    }

    @Override
    public void create(Ware objectToInsert) throws DaoException {
        PreparedStatement statement;

        try {
            long id = objectToInsert.getId();
            String bezeichnung = objectToInsert.getBezeichnung();
            String beschreibung = objectToInsert.getBeschreibung();
            double preis = objectToInsert.getPreis();
            String besonderheiten = objectToInsert.getBesonderheiten().toString();
            String maengel = objectToInsert.getMaengel().toString();

            connectionManager.getNewConnection();

            String sql = "INSERT INTO Ware (bezeichnung, beschreibung, preis, besonderheiten, maengel, id) VALUES (?,?,?,?,?,?)";
            statement = connectionManager.getExistingConnection().prepareStatement(sql);
            statement.setString(1, bezeichnung);
            statement.setString(2, beschreibung);
            statement.setDouble(3, preis);
            statement.setString(4, besonderheiten);
            statement.setString(5, maengel);
            statement.setLong(6, id);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Neue Ware hinzugefügt");
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        connectionManager.close(statement);
    }

    @Override
    public Ware read(Long id) throws DaoException {

        ResultSet resultSet;
        PreparedStatement statement;
        Ware ware;

        try {
            connectionManager.getNewConnection();
            String sql = "SELECT * FROM Ware WHERE id = " + id;
            statement = connectionManager.getExistingConnection().prepareStatement(sql);
            resultSet = statement.executeQuery();
            ware = new Ware(
                    resultSet.getLong("id"),
                    resultSet.getString("bezeichnung"),
                    resultSet.getString("beschreibung"),
                    resultSet.getDouble("preis"),
                    getListFromSavedList(resultSet.getString("besonderheiten")),
                    getListFromSavedList(resultSet.getString("maengel")));
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        connectionManager.close(resultSet, statement);
        return ware;
    }

    @Override
    public List<Ware> readAll() throws DaoException {

        ResultSet resultSet;
        Statement statement;
        List<Ware> waren = new ArrayList<>();

        try {
            connectionManager.getNewConnection();
            String sql = "SELECT * FROM Ware";
            statement = connectionManager.getExistingConnection().createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                long id = resultSet.getInt("id");
                String bezeichnung = resultSet.getString("bezeichnung");
                String beschreibung = resultSet.getString("beschreibung");
                double preis = resultSet.getDouble("preis");
                List<String> besonderheiten = getListFromSavedList(resultSet.getString("besonderheiten"));
                List<String> maengel = getListFromSavedList(resultSet.getString("maengel"));


                Ware ware = new Ware(id, bezeichnung, beschreibung, preis, besonderheiten, maengel);

                waren.add(ware);
            }
        } catch (SQLException | DaoException e) {
            throw new DaoException(e.getMessage());
        }
        connectionManager.close(resultSet, statement);
        return waren;
    }

    private List<String> getListFromSavedList(String savedList) {
        String[] split = savedList.substring(1, savedList.length() - 1).split(" ,");
        return Arrays.asList(split);
    }


    @Override
    public void update(Ware objectToUpdate) throws DaoException {
        Statement statement;
        try {
            connectionManager.getNewConnection();
            statement = connectionManager.getExistingConnection().createStatement();
            Ware ware = read(objectToUpdate.getId());
            List<String> aenderungen = getUnterschiedeInWaren(ware, objectToUpdate);

            if (aenderungen.isEmpty()) {
                return;
            }

            StringBuilder sql = new StringBuilder("UPDATE Ware SET ");
            int i = 0;
            for (String aenderung : aenderungen) {
                if (i > 0) {
                    sql.append(", ");
                }
                sql.append(aenderung);
                i++;
            }
            sql.append(" WHERE id = ").append(objectToUpdate.getId());
            statement.executeUpdate(String.valueOf(sql));
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        connectionManager.close(statement);
    }


    @Override
    public void delete(Long id) throws DaoException {
        Statement statement;
        try {
            connectionManager.getNewConnection();

            String sql = "DELETE FROM Ware WHERE id = " + id;
            statement = connectionManager.getExistingConnection().createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        connectionManager.close(statement);
    }

    private List<String> getUnterschiedeInWaren(Ware ware, Ware objectToUpdate) {
        List<String> unterschiede = new ArrayList<>();

        if (ware.equals(objectToUpdate)) {
            return unterschiede;
        }
        if (!ware.getBeschreibung().equals(objectToUpdate.getBeschreibung())) {
            unterschiede.add("beschreibung = '" + objectToUpdate.getBeschreibung() + "'");
        }
        if (!ware.getBezeichnung().equals(objectToUpdate.getBezeichnung())) {
            unterschiede.add("bezeichnung = '" + objectToUpdate.getBezeichnung() + "'");
        }
        if (!(ware.getPreis() == objectToUpdate.getPreis())) {
            unterschiede.add("preis = '" + objectToUpdate.getPreis() + "'");
        }
        return unterschiede;
    }
}

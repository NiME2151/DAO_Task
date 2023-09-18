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
            connectionManager.getNewConnection();
            Connection connection = connectionManager.getExistingConnection();

            long id = objectToInsert.getId();
            String bezeichnung = objectToInsert.getBezeichnung();
            String beschreibung = objectToInsert.getBeschreibung();
            double preis = objectToInsert.getPreis();
            String besonderheiten = objectToInsert.getBesonderheiten().toString();
            String maengel = objectToInsert.getMaengel().toString();

            String sql = "INSERT INTO Ware (bezeichnung, beschreibung, preis, besonderheiten, maengel, id) VALUES (?,?,?,?,?,?)";
            statement = connection.prepareStatement(sql);
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
            connection.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
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
            connectionManager.close(resultSet, statement, connectionManager.getExistingConnection());
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }

        return ware;
    }

    @Override
    public List<Ware> readAll() throws DaoException {

        ResultSet resultSet;
        PreparedStatement statement;
        List<Ware> waren = new ArrayList<>();

        try {
            connectionManager.getNewConnection();
            String sql = "SELECT * FROM Ware";
            statement = connectionManager.getExistingConnection().prepareStatement(sql);
            resultSet = statement.executeQuery();

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
            connectionManager.close(resultSet, statement, connectionManager.getExistingConnection());
        } catch (SQLException | DaoException e) {
            throw new DaoException(e.getMessage());
        }
        return waren;
    }

    private List<String> getListFromSavedList(String savedList) {
        String[] split = savedList.substring(1, savedList.length() - 1).split(" ,");
        return Arrays.asList(split);
    }


    @Override
    public void update(Ware objectToUpdate) throws DaoException {
        PreparedStatement statement;
        try {
            connectionManager.getNewConnection();
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
            statement = connectionManager.getExistingConnection().prepareStatement(String.valueOf(sql));
            statement.executeQuery();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }


    @Override
    public void delete(Long id) {

    }

    private List<String> getUnterschiedeInWaren(Ware ware, Ware objectToUpdate) {
        List<String> unterschiede = new ArrayList<>();

        if (ware.equals(objectToUpdate)) {
            return unterschiede;
        }
        if (!ware.getBeschreibung().equals(objectToUpdate.getBeschreibung())) {
            unterschiede.add("beschreibung =" + objectToUpdate.getBeschreibung());
        }
        if (!ware.getBezeichnung().equals(objectToUpdate.getBezeichnung())) {
            unterschiede.add("bezeichnung =" + objectToUpdate.getBezeichnung());
        }
        if (!(ware.getPreis() == objectToUpdate.getPreis())) {
            unterschiede.add("preis =" + objectToUpdate.getPreis());
        }
        return unterschiede;
    }
}

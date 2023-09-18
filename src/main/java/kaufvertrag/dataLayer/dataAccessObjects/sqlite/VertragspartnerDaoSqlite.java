package kaufvertrag.dataLayer.dataAccessObjects.sqlite;

import kaufvertrag.dataLayer.businessObjects.Adresse;
import kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import kaufvertrag.dataLayer.dataAccessObjects.IDao;
import kaufvertrag.exceptions.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VertragspartnerDaoSqlite implements IDao<Vertragspartner, String> {

    ConnectionManager connectionManager = new ConnectionManager();

    @Override
    public Vertragspartner create() {
        return null;
    }

    @Override
    public void create(Vertragspartner vertragspartner) throws DaoException {
        PreparedStatement statement;

        try {
            connectionManager.getNewConnection();


            String ausweisNr = vertragspartner.getAusweisNr();
            String vorname = vertragspartner.getVorname();
            String nachname = vertragspartner.getNachname();
            Adresse adresse = vertragspartner.getAdresse();

            String sql = "INSERT INTO Vertragspartner (ausweisNr, vorname, nachname, strasse, hausNr, plz, ort) VALUES (?,?,?,?,?,?,?)";
            statement = connectionManager.getExistingConnection().prepareStatement(sql);
            statement.setString(1, ausweisNr);
            statement.setString(2, vorname);
            statement.setString(3, nachname);
            statement.setString(4, adresse.getStrasse());
            statement.setString(5, adresse.getHausNr());
            statement.setString(6, adresse.getPlz());
            statement.setString(7, adresse.getOrt());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Neuen Vertragspartner hinzugefügt");
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        connectionManager.close(statement);
    }

    @Override
    public List<Vertragspartner> readAll() throws DaoException {
        ResultSet resultSet;
        Statement statement;
        List<Vertragspartner> vertragspartners = new ArrayList<>();

        try {
            connectionManager.getNewConnection();
            String sql = "SELECT * FROM Vertragspartner";
            statement = connectionManager.getExistingConnection().createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                String ausweisNr = resultSet.getString("ausweisNr");
                String vorname = resultSet.getString("vorname");
                String nachname = resultSet.getString("nachname");

                String strasse = resultSet.getString("strasse");
                String hausNr = resultSet.getString("hausNr");
                String plz = resultSet.getString("plz");
                String ort = resultSet.getString("ort");

                Adresse adresse = new Adresse(strasse, hausNr, plz, ort);

                Vertragspartner vertragspartner = new Vertragspartner(vorname, nachname, ausweisNr, adresse);

                vertragspartners.add(vertragspartner);
            }
        } catch (SQLException | DaoException e) {
            throw new DaoException(e.getMessage());
        }
        connectionManager.close(resultSet, statement);
        return vertragspartners;
    }

    @Override
    public Vertragspartner read(String ausweisNr) throws DaoException {
        ResultSet resultSet;
        Statement statement;
        Vertragspartner vertragspartner;
        try {
            connectionManager.getNewConnection();
            String sql = "SELECT * FROM Vertragspartner WHERE ausweisNr = " + ausweisNr;
            statement = connectionManager.getExistingConnection().createStatement();
            resultSet = statement.executeQuery(sql);

            String vorname = resultSet.getString("vorname");
            String nachname = resultSet.getString("nachname");

            String strasse = resultSet.getString("strasse");
            String hausNr = resultSet.getString("hausNr");
            String plz = resultSet.getString("plz");
            String ort = resultSet.getString("ort");

            Adresse adresse = new Adresse(strasse, hausNr, plz, ort);
            vertragspartner = new Vertragspartner(vorname, nachname, ausweisNr, adresse);

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        connectionManager.close(resultSet, statement);

        return vertragspartner;
    }

    @Override
    public void update(Vertragspartner vertragspartnerToUpdate) throws DaoException {
        Statement statement;
        try {
            connectionManager.getNewConnection();
            statement = connectionManager.getExistingConnection().createStatement();
            Vertragspartner vertragspartner = read(vertragspartnerToUpdate.getAusweisNr());
            List<String> aenderungen = getUnterschiedeInVertragspartner(vertragspartner, vertragspartnerToUpdate);

            if (aenderungen.isEmpty()) {
                return;
            }

            StringBuilder sql = new StringBuilder("UPDATE Vertragspartner SET ");
            int i = 0;
            for (String aenderung : aenderungen) {
                if (i > 0) {
                    sql.append(", ");
                }
                sql.append(aenderung);
                i++;
            }
            sql.append("WHERE ausweisNr = ").append(vertragspartnerToUpdate.getAusweisNr());
            statement.executeUpdate(String.valueOf(sql));
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        connectionManager.close(statement);
    }

    @Override
    public void delete(String id) throws DaoException {
        Statement statement;
        try {

            connectionManager.getNewConnection();

            String sql = "DELETE FROM Vertragspartner WHERE ausweisNr = " + id;
            statement = connectionManager.getExistingConnection().createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        connectionManager.close(statement);

    }

    private List<String> getUnterschiedeInVertragspartner(Vertragspartner vertragspartner, Vertragspartner objectToUpdate) {
        List<String> unterschiede = new ArrayList<>();
        Adresse adresse = vertragspartner.getAdresse();
        Adresse adresseToUpdate = objectToUpdate.getAdresse();

        if (vertragspartner.equals(objectToUpdate)) {
            return unterschiede;
        }
        if (!vertragspartner.getVorname().equals(objectToUpdate.getVorname())) {
            unterschiede.add("vorname = '" + objectToUpdate.getVorname() + "'");
        }
        if (!vertragspartner.getNachname().equals(objectToUpdate.getNachname())) {
            unterschiede.add("nachname = '" + objectToUpdate.getNachname() + "'");
        }
        if (!adresse.getHausNr().equals(adresseToUpdate.getHausNr())) {
            unterschiede.add("hausNr = '" + adresseToUpdate.getHausNr() + "'");
        }
        if (!adresse.getStrasse().equals(adresseToUpdate.getStrasse())) {
            unterschiede.add("strasse = '" + adresseToUpdate.getStrasse() + "'");
        }
        if (!adresse.getOrt().equals(adresseToUpdate.getOrt())) {
            unterschiede.add("ort = '" + adresseToUpdate.getOrt() + "'");
        }
        if (!adresse.getPlz().equals(adresseToUpdate.getPlz())) {
            unterschiede.add("plz = '" + adresseToUpdate.getPlz() + "'");
        }

        return unterschiede;
    }
}

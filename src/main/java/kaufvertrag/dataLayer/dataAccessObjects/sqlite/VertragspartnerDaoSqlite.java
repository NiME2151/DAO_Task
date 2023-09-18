package kaufvertrag.dataLayer.dataAccessObjects.sqlite;

import kaufvertrag.dataLayer.businessObjects.Adresse;
import kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import kaufvertrag.dataLayer.businessObjects.Ware;
import kaufvertrag.dataLayer.dataAccessObjects.IDao;
import kaufvertrag.exceptions.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            Connection connection = connectionManager.getExistingConnection();

            String ausweisNr = vertragspartner.getAusweisNr();
            String vorname = vertragspartner.getVorname();
            String nachname = vertragspartner.getNachname();
            Adresse adresse = vertragspartner.getAdresse();

            String sql = "INSERT INTO Vertragspartner (ausweisNr, vorname, nachname, strasse, hausNr, plz, ort) VALUES (?,?,?,?,?,?,?)";
            statement = connection.prepareStatement(sql);
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
            connection.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public List<Vertragspartner> readAll() throws DaoException {
        ResultSet resultSet;
        PreparedStatement statement;
        List<Vertragspartner> vertragspartners = new ArrayList<>();

        try {
            connectionManager.getNewConnection();
            String sql = "SELECT * FROM Vertragspartner";
            statement = connectionManager.getExistingConnection().prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String ausweisNr = resultSet.getString("ausweisNr");
                String vorname = resultSet.getString("vorname");
                String nachname = resultSet.getString("nachname");

                String strasse = resultSet.getString("strasse");
                String hausNr = resultSet.getString("hausNr");
                String plz = resultSet.getString("plz");
                String ort = resultSet.getString("ort");

                Adresse adresse = new Adresse(strasse, hausNr, plz, ort);

                Vertragspartner vertragspartner = new Vertragspartner(vorname,nachname,ausweisNr,adresse);

                vertragspartners.add(vertragspartner);
            }
            connectionManager.close(resultSet, statement, connectionManager.getExistingConnection());
        } catch (SQLException | DaoException e) {
            throw new DaoException(e.getMessage());
        }
        return vertragspartners;
    }

    @Override
    public Vertragspartner read(String id) throws DaoException {
        return null;
    }

    @Override
    public void update(Vertragspartner vertragspartner) throws DaoException {

    }

    @Override
    public void delete(String id) {

    }
}

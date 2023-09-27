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
            
            String sqlAdresse = "INSERT INTO Adressen (strasse, hausNr, plz, ort) VALUES(?,?,?,?);";
            PreparedStatement addressStatement = connectionManager.getExistingConnection().prepareStatement(sqlAdresse, Statement.RETURN_GENERATED_KEYS);
            addressStatement.setString(1, adresse.getStrasse());
            addressStatement.setString(2, adresse.getHausNr());
            addressStatement.setString(3, adresse.getPlz());
            addressStatement.setString(4, adresse.getOrt());

            int rowsInsertedAdresse = addressStatement.executeUpdate();

            if (rowsInsertedAdresse > 0) {
                ResultSet generatedKeys = addressStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int addressId = generatedKeys.getInt(1);
                    String sqlVertragspartner = "INSERT INTO Vertragspartner (ausweisNr, vorname, nachname, adresseId) VALUES (?,?,?,?);";
                    PreparedStatement partnerStatement = connectionManager.getExistingConnection().prepareStatement(sqlVertragspartner);
                    partnerStatement.setString(1, ausweisNr);
                    partnerStatement.setString(2, vorname);
                    partnerStatement.setString(3, nachname);
                    partnerStatement.setInt(4, addressId);

                    int rowsInsertedVertragspartner = partnerStatement.executeUpdate();

                    if (rowsInsertedVertragspartner > 0) {
                        System.out.println("Neuer Vertragspartner hinzugefügt");
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
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
                Vertragspartner vertragspartner = getVertragsparterWithAdress(resultSet, connectionManager);
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

            vertragspartner = getVertragsparterWithAdress(resultSet, connectionManager);

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        connectionManager.close(resultSet, statement);

        return vertragspartner;
    }

    
    //// AHHHHHH Adresse!
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
        ResultSet resultSet;
        Statement statementAdresse;
        try {
            connectionManager.getNewConnection();
            String adressSQL = "GET adresseId FROM Vertragspartner WHERE ausweisNr =" + id;
            statementAdresse = connectionManager.getExistingConnection().createStatement();
            int adresseId = statementAdresse.executeUpdate(adressSQL);
            
            String deleteAdressSQL = "DELETE FROM Adresse WHERE adressID = " + adresseId;
            String sql = "DELETE FROM Vertragspartner WHERE ausweisNr = " + id;
            statement = connectionManager.getExistingConnection().createStatement();
            statement.executeUpdate(deleteAdressSQL);
        } catch (SQLException e) {
            throw new DaoException("Problem beim löschen der Adresse");
        };
        
        try {
            connectionManager.getNewConnection();
            String adressSQL = "GET adresseId FROM Vertragspartner WHERE ausweisNr =" + id;
            statementAdresse = connectionManager.getExistingConnection().createStatement();
            int adresseId = statementAdresse.executeUpdate(adressSQL);

            String deleteAdressSQL = "DELETE FROM Adresse WHERE adressID = " + adresseId;
            String sql = "DELETE FROM Vertragspartner WHERE ausweisNr = " + id;
            statement = connectionManager.getExistingConnection().createStatement();
            statement.executeUpdate(deleteAdressSQL);
        } catch (SQLException e) {
            throw new DaoException("Problem beim löschen der Adresse");
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
    private Vertragspartner getVertragsparterWithAdress(ResultSet resultSet, ConnectionManager connectionManager) throws SQLException, DaoException {
        String ausweisNr = resultSet.getString("ausweisNr");
        String vorname = resultSet.getString("vorname");
        String nachname = resultSet.getString("nachname");
        int adresseId = resultSet.getInt("adresseId");

        ResultSet resultSetAdresse;
        Statement statementAdresse;
        try {
            String sqlAdresse = "SELECT * FROM Adressen WHERE id=" + adresseId;
            statementAdresse = connectionManager.getExistingConnection().createStatement();
            resultSetAdresse = statementAdresse.executeQuery(sqlAdresse);

            String strasse = resultSetAdresse.getString("strasse");
            String hausNr = resultSetAdresse.getString("hausNr");
            String plz = resultSetAdresse.getString("plz");
            String ort = resultSetAdresse.getString("ort");

            Adresse adresse = new Adresse(strasse, hausNr, plz, ort);

            return new Vertragspartner(vorname, nachname, ausweisNr, adresse);
        } catch (SQLException e){
            throw new DaoException(e.getMessage());
        }

    }
}

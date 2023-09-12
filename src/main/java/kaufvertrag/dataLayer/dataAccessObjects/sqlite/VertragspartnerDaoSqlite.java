package kaufvertrag.dataLayer.dataAccessObjects.sqlite;

import kaufvertrag.businessObjects.IVertragspartner;
import kaufvertrag.dataLayer.businessObjects.Adresse;
import kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import kaufvertrag.dataLayer.businessObjects.Ware;
import kaufvertrag.dataLayer.dataAccessObjects.IVertragspartnerDao;
import kaufvertrag.exceptions.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class VertragspartnerDaoSqlite implements IVertragspartnerDao {
    @Override
    public Vertragspartner create() {
        return new Vertragspartner();
    }

    @Override
    public Vertragspartner create(IVertragspartner vertragspartner) throws DaoException {
        PreparedStatement statement;
        try {
            ConnectionManager.getNewConnection();

            String ausweisnummer = vertragspartner.getAusweisNr();
            String vorname = vertragspartner.getVorname();
            String nachname = vertragspartner.getNachname();
            String adresse = vertragspartner.getAdresse().toString();

            String sqlStatement = "INSERT INTO Vertragspartner (ausweisnummer, vorname, nachname, adresse) VALUES (?,?,?,?)";
            
            statement = ConnectionManager.getExistingConnection().prepareStatement(sqlStatement);
            statement.setString(1,ausweisnummer);
            statement.setString(2,vorname);
            statement.setString(3,nachname);
            statement.setString(4,adresse);
           
            int rowsInserted =  statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Neuer Vertragspartner hinzugefügt");
            }
            ConnectionManager.close(null, statement , ConnectionManager.getExistingConnection());
        } catch (SQLException | DaoException e) {
            throw new DaoException(e.getMessage());
        }
    }
    
    @Override
    public Vertragspartner read(int id) throws DaoException {
        ResultSet resultSet;
        PreparedStatement statement;
        Vertragspartner vertragspartner;

        try {
            ConnectionManager.getNewConnection();
            String sqlStatement = "SELECT * FROM Vertragspartner WHERE id = " + id;
            statement = ConnectionManager.getExistingConnection().prepareStatement(sqlStatement);
            resultSet = statement.executeQuery();

            List<String> adr = List.of(resultSet.getString("adresse").split(","));
            Adresse adresse = new Adresse(adr.get(0), adr.get(1), adr.get(2), adr.get(3));
            vertragspartner = new Vertragspartner(
                    resultSet.getString("vorname"),
                    resultSet.getString("nachname"),
                    resultSet.getString("ausweisnummer"),
                    adresse
            );
            ConnectionManager.close(resultSet,statement,ConnectionManager.getExistingConnection());
        }
        catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }

        return vertragspartner;
    }

    @Override
    public List<Vertragspartner> read() throws DaoException {
        return null;
    }
    
    @Override
    public void update(IVertragspartner vertragspartner) throws DaoException {

    }

    @Override
    public void delete(int id) throws DaoException {

    }
}

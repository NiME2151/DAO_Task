package kaufvertrag.dataLayer.dataAccessObjects.sqlite;

import kaufvertrag.dataLayer.businessObjects.Ware;
import kaufvertrag.dataLayer.dataAccessObjects.IDao;
import kaufvertrag.exceptions.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class WareDaoSqlite implements IDao<Ware, Long> {

    @Override
    public Ware create() {
        return (new Ware());
    }

    @Override
    public void create(Ware objectToInsert) throws DaoException {
        PreparedStatement statement;
        
        try {
            ConnectionManager.getNewConnection();

            String bezeichnung = objectToInsert.getBezeichnung();
            String beschreibung = objectToInsert.getBeschreibung();
            double preis = objectToInsert.getPreis();
            String besonderheiten = objectToInsert.getBesonderheiten().toString();
            String maengel = objectToInsert.getMaengel().toString();

            String sql = "INSERT INTO Ware (bezeichnung, beschreibung, preis, besonderheiten, maengel) VALUES (?,?,?,?,?)";
            statement = ConnectionManager.getExistingConnection().prepareStatement(sql);
            statement.setString(1,bezeichnung);
            statement.setString(2,beschreibung);
            statement.setDouble(3,preis);
            statement.setString(4,besonderheiten);
            statement.setString(5,maengel);
            int rowsInserted =  statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Neue Ware hinzugefügt");
            }
            ConnectionManager.close(null, statement , ConnectionManager.getExistingConnection());
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
            ConnectionManager.getNewConnection();
            String sql = "SELECT * FROM Ware WHERE id = " + id;
            statement = ConnectionManager.getExistingConnection().prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            List<String> besonderheiten = List.of(resultSet.getString("besonderheiten").split(","));
            List<String> maengel = List.of(resultSet.getString("maengel").split(","));
            ware = new Ware(
                    id,
                    resultSet.getString("bezeichnung"),
                    resultSet.getString("beschreibung"),
                    resultSet.getDouble("preis"),
                    besonderheiten,
                    maengel)
            ConnectionManager.close(resultSet,statement,ConnectionManager.getExistingConnection());
        } 
        catch (SQLException e) {
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
            ConnectionManager.getNewConnection();
            String sql = "SELECT * FROM Ware";
            statement = ConnectionManager.getExistingConnection().prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                
                long id = resultSet.getInt("userid");
                String bezeichnung = resultSet.getString("bezeichnung"); 
                String beschreibung = resultSet.getString("beschreibung");
                double preis = resultSet.getDouble("preis");
                List<String> besonderheiten = List.of(resultSet.getString("besonderheiten").split(","));
                List<String> maengel = List.of(resultSet.getString("maengel").split(","));

                
                Ware ware = new Ware(id, bezeichnung, beschreibung, preis, besonderheiten, maengel);

                waren.add(ware);
            }
            ConnectionManager.close(resultSet,statement,ConnectionManager.getExistingConnection());
        }
        catch (SQLException | DaoException e) {
            throw new DaoException(e.getMessage());
        }
        return waren;
    }
    

    @Override
    public void update(Ware objectToUpdate) {

    }

    @Override
    public void delete(Long id) {

    }
}

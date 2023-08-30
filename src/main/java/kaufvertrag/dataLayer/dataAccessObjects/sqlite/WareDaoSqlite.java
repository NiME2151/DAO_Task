package kaufvertrag.dataLayer.dataAccessObjects.sqlite;

import kaufvertrag.dataLayer.businessObjects.Ware;
import kaufvertrag.dataLayer.dataAccessObjects.IDao;
import kaufvertrag.exceptions.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public void update(Ware objectToUpdate) throws DaoException {
        PreparedStatement statement;
        try {
            ConnectionManager.getNewConnection();
            Ware ware = read(objectToUpdate.getId());
            List <String> aenderungen = getUnterschiedeInWaren(ware, objectToUpdate);
            
            if (aenderungen.isEmpty()){
                return;
            }

            StringBuilder sql = new StringBuilder("UPDATE Ware SET ");
            int i = 0;
            for (String aenderung: aenderungen) {
                if (i > 0){
                    sql.append(", ");
                }
                sql.append(aenderung);
                i++;
            }
            statement = ConnectionManager.getExistingConnection().prepareStatement(String.valueOf(sql));
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
        
        if (ware.equals(objectToUpdate)){
            return unterschiede;
        }
        if (!ware.getBeschreibung().equals(objectToUpdate.getBeschreibung())){
            unterschiede.add("beschreibung =" + objectToUpdate.getBeschreibung());
        }
        if (!ware.getBezeichnung().equals(objectToUpdate.getBezeichnung())){
            unterschiede.add("bezeichnung =" + objectToUpdate.getBezeichnung());
        }
        if (!(ware.getPreis() == objectToUpdate.getPreis())){
            unterschiede.add("preis =" + objectToUpdate.getPreis());
        }
        if (!ware.getBesonderheiten().equals(objectToUpdate.getBesonderheiten())){
            unterschiede.add("besonderheiten =" + objectToUpdate.getBesonderheiten().toString());
        }
        if (!ware.getMaengel().equals(objectToUpdate.getMaengel())){
            unterschiede.add("maengel =" + objectToUpdate.getMaengel().toString());
        }
        return unterschiede;
    }
}

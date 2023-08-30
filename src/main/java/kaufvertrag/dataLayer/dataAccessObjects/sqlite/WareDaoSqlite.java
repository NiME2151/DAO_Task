package kaufvertrag.dataLayer.dataAccessObjects.sqlite;

import kaufvertrag.dataLayer.businessObjects.Ware;
import kaufvertrag.dataLayer.dataAccessObjects.IDao;
import kaufvertrag.exceptions.DaoException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class WareDaoSqlite implements IDao<Ware, Long> {

    @Override
    public Ware create() {
        return (new Ware("", 0.0));
    }

    @Override
    public void create(Ware objectToInsert) throws DaoException {
        ConnectionManager.getNewConnection();

        String bezeichnung = objectToInsert.getBezeichnung();
        String beschreibung = objectToInsert.getBeschreibung();
        double preis = objectToInsert.getPreis();
        String besonderheiten = objectToInsert.getBesonderheiten().toString();
        String maengel = objectToInsert.getMaengel().toString();
        PreparedStatement statement;

        String sql = "INSERT INTO Ware (bezeichnung, beschreibung, preis, besonderheiten, maengel) VALUES (?,?,?,?,?)";
        try {
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
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        ConnectionManager.close(null, statement , ConnectionManager.getExistingConnection());
    }

    @Override
    public Ware read(Long id) {

        return null;
    }

    @Override
    public List<Ware> readAll() {
        return null;
    }

    @Override
    public void update(Ware objectToUpdate) {

    }

    @Override
    public void delete(Long id) {

    }
}

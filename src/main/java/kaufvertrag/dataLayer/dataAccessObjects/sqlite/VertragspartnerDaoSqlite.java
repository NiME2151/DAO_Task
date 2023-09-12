package kaufvertrag.dataLayer.dataAccessObjects.sqlite;

import kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import kaufvertrag.dataLayer.dataAccessObjects.IDao;
import kaufvertrag.exceptions.DaoException;

import java.util.List;

public class VertragspartnerDaoSqlite implements IDao<Vertragspartner,String> {
    @Override
    public Vertragspartner create() {
        return null;
    }

    @Override
    public void create(Vertragspartner vertragspartner) throws DaoException {
    }

    @Override
    public List<Vertragspartner> readAll() throws DaoException {
        return null;
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

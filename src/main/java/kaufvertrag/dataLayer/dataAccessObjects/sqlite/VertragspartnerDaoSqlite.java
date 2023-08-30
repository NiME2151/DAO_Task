package kaufvertrag.dataLayer.dataAccessObjects.sqlite;

import kaufvertrag.businessObjects.IVertragspartner;
import kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import kaufvertrag.dataLayer.dataAccessObjects.IVertragspartnerDao;
import kaufvertrag.exceptions.DaoException;

import java.util.List;

public class VertragspartnerDaoSqlite implements IVertragspartnerDao {
    @Override
    public IVertragspartner create() {
        return null;
    }

    @Override
    public IVertragspartner create(IVertragspartner vertragspartner) throws DaoException {
        return null;
    }

    @Override
    public List<Vertragspartner> read() throws DaoException {
        return null;
    }

    @Override
    public IVertragspartner read(int id) throws DaoException {
        return null;
    }

    @Override
    public void update(IVertragspartner vertragspartner) throws DaoException {

    }

    @Override
    public void delete(int id) throws DaoException {

    }
}

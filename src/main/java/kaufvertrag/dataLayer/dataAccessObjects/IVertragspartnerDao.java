package kaufvertrag.dataLayer.dataAccessObjects;


import kaufvertrag.businessObjects.IVertragspartner;
import kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import kaufvertrag.exceptions.DaoException;

import java.util.List;

public interface IVertragspartnerDao {

    Vertragspartner create();

    Vertragspartner create(IVertragspartner vertragspartner) throws DaoException;

    List<Vertragspartner> read() throws DaoException;

    Vertragspartner read(int id) throws DaoException;

    void update(IVertragspartner vertragspartner) throws DaoException;

    void delete(int id) throws DaoException;
}

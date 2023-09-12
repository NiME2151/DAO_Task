package kaufvertrag.dataLayer.dataAccessObjects;

import kaufvertrag.dataLayer.businessObjects.Ware;
import kaufvertrag.exceptions.DaoException;

import java.util.List;

public interface IWareDao {
    Ware create() throws DaoException;

    Ware create(Ware ware) throws DaoException;

    List<Ware> read() throws DaoException;

    Ware read(int id) throws DaoException;

    void update(Ware ware) throws DaoException;

    void delete(int id) throws DaoException;
}

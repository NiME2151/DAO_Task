package kaufvertrag.dataLayer.dataAccessObjects;

import kaufvertrag.businessObjects.IWare;
import kaufvertrag.dataLayer.businessObjects.Ware;
import kaufvertrag.exceptions.DaoException;

import java.util.List;

public interface IWareDao {
    IWare create() throws DaoException;

    IWare create(IWare ware) throws DaoException;

    List<Ware> read() throws DaoException;

    IWare read(int id) throws DaoException;

    void update(IWare ware) throws DaoException;

    void delete(int id) throws DaoException;
}

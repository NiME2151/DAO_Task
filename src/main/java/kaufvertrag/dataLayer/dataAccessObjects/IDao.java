package kaufvertrag.dataLayer.dataAccessObjects;

import kaufvertrag.exceptions.DaoException;

import java.util.List;

public interface IDao<T, K> {

    T create();
    void create(T objectToInsert) throws DaoException;
    T read(K id) throws DaoException;
    List<T> readAll() throws DaoException;
    void update(T objectToUpdate) throws DaoException;
    void delete(K id);
}

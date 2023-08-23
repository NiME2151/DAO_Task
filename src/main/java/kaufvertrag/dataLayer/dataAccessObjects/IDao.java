package kaufvertrag.dataLayer.dataAccessObjects;

import kaufvertrag.exceptions.DaoException;

import java.util.List;

public interface IDao<T, K> {

    T create();
    void create(T objectToInsert) throws DaoException;
    T read(K id);
    List<T> readAll();
    void update(T objectToUpdate);
    void delete(K id);
}

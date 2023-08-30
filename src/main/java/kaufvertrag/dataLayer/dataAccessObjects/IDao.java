package kaufvertrag.dataLayer.dataAccessObjects;

import kaufvertrag.dataLayer.businessObjects.Vertragspartner;

import java.util.List;

public interface IDao<T, K> {

    T create();
    void create(T objectToInsert);
    T read(K id);
    List<Vertragspartner> readAll();
    void update(T objectToUpdate);
    void delete(K id);
}

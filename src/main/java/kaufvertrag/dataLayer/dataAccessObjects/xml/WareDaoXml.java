package kaufvertrag.dataLayer.dataAccessObjects.xml;

import kaufvertrag.dataLayer.businessObjects.Ware;
import kaufvertrag.dataLayer.dataAccessObjects.IDao;

import java.util.List;

public class WareDaoXml implements IDao<Ware, Long> {


    @Override
    public Ware create() {
        return null;
    }

    @Override
    public void create(Ware objectToInsert){
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

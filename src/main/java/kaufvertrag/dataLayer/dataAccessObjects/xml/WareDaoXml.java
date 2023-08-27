package kaufvertrag.dataLayer.dataAccessObjects.xml;

import kaufvertrag.businessObjects.IWare;
import kaufvertrag.dataLayer.businessObjects.Ware;
import kaufvertrag.dataLayer.dataAccessObjects.IDao;
import kaufvertrag.exceptions.DaoException;

import java.util.List;

public class WareDaoXml implements IDao<IWare, Long> {

    @Override
    public IWare create() {
        return new Ware("", 0);
    }

    @Override
    public void create(IWare objectToInsert) throws DaoException {
        Ware ware = new Ware(objectToInsert.getBezeichnung(), objectToInsert.getPreis());
    }

    @Override
    public IWare read(Long id) {
        return null;
    }

    @Override
    public List<IWare> readAll() {
        return null;
    }

    @Override
    public void update(IWare objectToUpdate) {

    }

    @Override
    public void delete(Long id) {

    }
}

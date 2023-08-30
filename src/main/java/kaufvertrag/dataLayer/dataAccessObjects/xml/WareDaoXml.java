package kaufvertrag.dataLayer.dataAccessObjects.xml;

import kaufvertrag.businessObjects.IWare;
import kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import kaufvertrag.dataLayer.dataAccessObjects.IDao;

import java.util.List;

public class WareDaoXml implements IDao<IWare, Long> {


    @Override
    public IWare create() {
        return null;
    }

    @Override
    public void create(IWare objectToInsert) {

    }

    @Override
    public IWare read(Long id) {
        return null;
    }

    @Override
    public List<Vertragspartner> readAll() {
        return null;
    }

    @Override
    public void update(IWare objectToUpdate) {

    }

    @Override
    public void delete(Long id) {

    }
}

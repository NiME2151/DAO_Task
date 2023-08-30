package kaufvertrag.dataLayer.dataAccessObjects.sqlite;

import kaufvertrag.businessObjects.IVertragspartner;
import kaufvertrag.businessObjects.IWare;
import kaufvertrag.dataLayer.dataAccessObjects.IDao;
import kaufvertrag.dataLayer.dataAccessObjects.IDataLayer;

public class DataLayerSqlite implements IDataLayer {

    @Override
    public IDao<IVertragspartner, String> getVertragspartnerDao() {
        return null;
    }

    @Override
    public IDao<IWare, Long> getWareDao() {
        return null;
    }
}

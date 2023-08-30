package kaufvertrag.dataLayer.dataAccessObjects.sqlite;

import kaufvertrag.dataLayer.dataAccessObjects.IDataLayer;
import kaufvertrag.dataLayer.dataAccessObjects.IVertragspartnerDao;
import kaufvertrag.dataLayer.dataAccessObjects.IWareDao;

public class DataLayerSqlite implements IDataLayer {

    @Override
    public IVertragspartnerDao getVertragspartnerDao() {
        return null;
    }

    @Override
    public IWareDao getWareDao() {
        return null;
    }
}

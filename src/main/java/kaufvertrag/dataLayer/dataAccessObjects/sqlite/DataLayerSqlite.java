package kaufvertrag.dataLayer.dataAccessObjects.sqlite;

import kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import kaufvertrag.dataLayer.businessObjects.Ware;
import kaufvertrag.dataLayer.dataAccessObjects.IDao;
import kaufvertrag.dataLayer.dataAccessObjects.IDataLayer;

public class DataLayerSqlite implements IDataLayer {

    @Override
    public IDao<Vertragspartner, String> getVertragspartnerDao() {
        return null;
    }

    @Override
    public IDao<Ware, Long> getWareDao() {
        return new WareDaoSqlite();
    }
}

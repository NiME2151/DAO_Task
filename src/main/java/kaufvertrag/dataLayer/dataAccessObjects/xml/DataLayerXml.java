package kaufvertrag.dataLayer.dataAccessObjects.xml;

import kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import kaufvertrag.dataLayer.businessObjects.Ware;
import kaufvertrag.dataLayer.dataAccessObjects.IDao;
import kaufvertrag.dataLayer.dataAccessObjects.IDataLayer;

public class DataLayerXml implements IDataLayer {

    @Override
    public IDao<Vertragspartner, String> getVertragspartnerDao() {
        return new VertragspartnerDaoXml();
    }

    @Override
    public IDao<Ware, Long> getWareDao() {
        return new WareDaoXml();
    }
}

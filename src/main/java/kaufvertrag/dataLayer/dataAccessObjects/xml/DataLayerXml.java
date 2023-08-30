package kaufvertrag.dataLayer.dataAccessObjects.xml;

import kaufvertrag.businessObjects.IVertragspartner;
import kaufvertrag.businessObjects.IWare;
import kaufvertrag.dataLayer.dataAccessObjects.IDao;
import kaufvertrag.dataLayer.dataAccessObjects.IDataLayer;

public class DataLayerXml implements IDataLayer {

    @Override
    public IDao<IVertragspartner, String> getVertragspartnerDao() {
        return new VertragspartnerDaoXml();
    }

    @Override
    public IDao<IWare, Long> getWareDao() {
        return new WareDaoXml();
    }
}

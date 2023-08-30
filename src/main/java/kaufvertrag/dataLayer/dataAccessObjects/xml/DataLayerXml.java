package kaufvertrag.dataLayer.dataAccessObjects.xml;

import kaufvertrag.dataLayer.dataAccessObjects.IDataLayer;

public class DataLayerXml implements IDataLayer {

    @Override
    public VertragspartnerDaoXml getDaoVertragspartner() {
        return new VertragspartnerDaoXml();
    }

    @Override
    public WareDaoXml getDaoWare() {
        return new WareDaoXml();
    }
}

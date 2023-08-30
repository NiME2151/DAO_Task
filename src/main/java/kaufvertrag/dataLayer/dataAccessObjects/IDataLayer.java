package kaufvertrag.dataLayer.dataAccessObjects;

import kaufvertrag.dataLayer.dataAccessObjects.xml.VertragspartnerDaoXml;
import kaufvertrag.dataLayer.dataAccessObjects.xml.WareDaoXml;

public interface IDataLayer {

    VertragspartnerDaoXml getDaoVertragspartner();
    WareDaoXml getDaoWare();
}

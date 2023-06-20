package kaufvertrag.dataLayer.dataAccessObjects;

import kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import kaufvertrag.dataLayer.businessObjects.Ware;

public interface IDataLayer {

    IDao<Vertragspartner, String> getDaoVertragspartner();
    IDao<Ware, String> getDaoWare();
}

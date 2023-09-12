package kaufvertrag.dataLayer.dataAccessObjects;

import kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import kaufvertrag.dataLayer.businessObjects.Ware;

public interface IDataLayer {
    IDao<Vertragspartner, String> getVertragspartnerDao();

    IDao<Ware, Long> getWareDao();
}

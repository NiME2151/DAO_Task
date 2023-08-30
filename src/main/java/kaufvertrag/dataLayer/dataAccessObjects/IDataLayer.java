package kaufvertrag.dataLayer.dataAccessObjects;

import kaufvertrag.businessObjects.IVertragspartner;
import kaufvertrag.businessObjects.IWare;
import kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import kaufvertrag.dataLayer.businessObjects.Ware;

public interface IDataLayer {
    IDao<IVertragspartner, String> getVertragspartnerDao();
    IDao<IWare, Long> getWareDao();
}

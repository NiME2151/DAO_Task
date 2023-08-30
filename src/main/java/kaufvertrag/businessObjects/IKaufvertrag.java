package kaufvertrag.businessObjects;

import kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import kaufvertrag.dataLayer.businessObjects.Ware;

public interface IKaufvertrag {

    Vertragspartner getVerkaeufer();

    void setVerkaeufer(Vertragspartner verkaeufer);

    Vertragspartner getKaeufer();

    void setKaeufer(Vertragspartner kaeufer);

    Ware getWare();

    void setWare(Ware ware);

    String getZahlungsModalitaeten();

    void setZahlungsModalitaeten(String zahlungsModalitaeten);
}

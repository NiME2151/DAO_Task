package kaufvertrag.businessObjects;

import java.util.List;

public interface IWare {

    long getId();
    void setId(long id);
    String getBezeichnung();
    void setBezeichnung(String bezeichnung);
    String getBeschreibung();
    void setBeschreibung(String beschreibung);
    double getPreis();
    void setPreis(double preis);
    List<String> getBesonderheiten();
    List<String> getMaengel();
}

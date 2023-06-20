package kaufvertrag.dataLayer.businessObjects;

import kaufvertrag.businessObjects.IAdresse;

public class Adresse implements IAdresse {

    private String strasse;
    private String hausNr;
    private String plz;
    private String ort;

    public Adresse(String strasse, String hausNr, String plz, String ort) {
        this.strasse = strasse;
        this.hausNr = hausNr;
        this.plz = plz;
        this.ort = ort;
    }

    @Override
    public String getStrasse() {
        return strasse;
    }

    @Override
    public String getHausNr() {
        return hausNr;
    }

    @Override
    public String getPlz() {
        return plz;
    }

    @Override
    public String getOrt() {
        return ort;
    }

    @Override
    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    @Override
    public void setHausNr(String hausNr) {
        this.hausNr = hausNr;
    }

    @Override
    public void setPlz(String plz) {
        this.plz = plz;
    }

    @Override
    public void setOrt(String ort) {
        this.ort = ort;
    }

    @Override
    public String toString() {
        return "kaufvertrag.dataLayer.businessObjects.Adresse{" +
                "strasse='" + strasse + '\'' +
                ", hausNr='" + hausNr + '\'' +
                ", plz='" + plz + '\'' +
                ", ort='" + ort + '\'' +
                '}';
    }
}

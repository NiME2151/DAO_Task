package kaufvertrag.dataLayer.businessObjects;

import kaufvertrag.businessObjects.IAdresse;
import kaufvertrag.businessObjects.IVertragspartner;

public class Vertragspartner implements IVertragspartner {

    private String ausweisNr;
    private String vorname;
    private String nachname;
    private Adresse adresse;

    public Vertragspartner(String vorname, String nachname) {
        this.vorname = vorname;
        this.nachname = nachname;
    }

    public Vertragspartner(){};

    @Override
    public String getAusweisNr() {
        return ausweisNr;
    }

    @Override
    public void setAusweisNr(String ausweisNr) {
        this.ausweisNr = ausweisNr;
    }

    @Override
    public String getVorname() {
        return vorname;
    }

    @Override
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    @Override
    public String getNachname() {
        return nachname;
    }

    @Override
    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    @Override
    public IAdresse getAdresse() {
        return adresse;
    }

    @Override
    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        String text = vorname + " " + nachname;
        text += "\n\t\tAusweisNr: " + ausweisNr;
        text += "\n\t\tAdresse: " + adresse;
        return text;
    }
}

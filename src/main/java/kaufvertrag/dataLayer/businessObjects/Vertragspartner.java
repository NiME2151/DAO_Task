package kaufvertrag.dataLayer.businessObjects;

import kaufvertrag.businessObjects.IAdresse;
import kaufvertrag.businessObjects.IVertragspartner;

import javax.xml.bind.annotation.XmlElement;

public class Vertragspartner implements IVertragspartner {

    private String ausweisNr;
    private String vorname;
    private String nachname;
    private IAdresse adresse;

    public Vertragspartner(String vorname, String nachname) {
        this.vorname = vorname;
        this.nachname = nachname;
    }

    @Override
    @XmlElement(name = "ausweis_nr")
    public String getAusweisNr() {
        return ausweisNr;
    }

    @Override
    public void setAusweisNr(String ausweisNr) {
        this.ausweisNr = ausweisNr;
    }

    @Override
    @XmlElement(name = "vorname")
    public String getVorname() {
        return vorname;
    }

    @Override
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    @Override
    @XmlElement(name = "nachname")
    public String getNachname() {
        return nachname;
    }

    @Override
    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    @Override
    @XmlElement(name = "adresse", type = Adresse.class)
    public IAdresse getAdresse() {
        return adresse;
    }

    @Override
    public void setAdresse(IAdresse adresse) {
        this.adresse = adresse;
    }
}

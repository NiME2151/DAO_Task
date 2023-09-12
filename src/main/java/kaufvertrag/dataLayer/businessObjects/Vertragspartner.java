package kaufvertrag.dataLayer.businessObjects;

import kaufvertrag.businessObjects.IVertragspartner;

import javax.xml.bind.annotation.XmlElement;

public class Vertragspartner implements IVertragspartner {

    private String ausweisNr;

    private String vorname;

    private String nachname;
    private Adresse adresse;
    
    public Vertragspartner(String vorname, String nachname, String ausweisNr, Adresse adresse) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.adresse = adresse;
        this.ausweisNr = ausweisNr;
    }
    
    public Vertragspartner(String vorname, String nachname) {
        this.vorname = vorname;
        this.nachname = nachname;
    }

    public Vertragspartner() {
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
    public Adresse getAdresse() {
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
        text += "\n\t\tAdresse: " + adresse.toString();
        return text;
    }
}

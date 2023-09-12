package kaufvertrag.dataLayer.businessObjects;

import jakarta.xml.bind.annotation.XmlRootElement;
import kaufvertrag.businessObjects.IVertragspartner;

@XmlRootElement(name = "vertragspartner")

public class Vertragspartner implements IVertragspartner {

    private String ausweisNr;
    private String vorname;
    private String nachname;
    private Adresse adresse;


    //noArgs Konstruktor, damit xml geschrieben werden kann; wirft sonst IllegalAnnotationException
    public Vertragspartner() {

    }

    public Vertragspartner(String vorname, String nachname) {
        this.vorname = vorname;
        this.nachname = nachname;
    }

    public Vertragspartner(String vorname, String nachname, String ausweisNr, Adresse adresse) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.adresse = adresse;
        this.ausweisNr = ausweisNr;
    }

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
        text += "\nAdresse: " + adresse.toString();
        return text;
    }
}

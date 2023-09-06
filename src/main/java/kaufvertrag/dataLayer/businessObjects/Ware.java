package kaufvertrag.dataLayer.businessObjects;

import jakarta.xml.bind.annotation.XmlRootElement;
import kaufvertrag.businessObjects.IWare;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "ware")
public class Ware implements IWare {

    private long id;
    private String bezeichnung;
    private String beschreibung;
    private double preis;
    private List<String> besonderheiten = new ArrayList<>();
    private List<String> maengel = new ArrayList<>();

    public Ware() {
    }

    public Ware(String bezeichnung, double preis) {
        this.bezeichnung = bezeichnung;
        this.preis = preis;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getBezeichnung() {
        return bezeichnung;
    }

    @Override
    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    @Override
    public String getBeschreibung() {
        return beschreibung;
    }

    @Override
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    @Override
    public double getPreis() {
        return preis;
    }

    @Override
    public void setPreis(double preis) {
        this.preis = preis;
    }

    @Override
    public List<String> getBesonderheiten() {
        return besonderheiten;
    }

    @Override
    public List<String> getMaengel() {
        return maengel;
    }

    @Override
    public String toString() {
        String text = "\n\t\tBezeichnung: " + bezeichnung;
        text += "\n\t\tID: " + id;
        text += "\n\t\tBeschreibung: " + beschreibung;
        text += "\n\t\tPreis: " + preis;
        text += "\n\t\tBesonderheiten: " + besonderheiten.toString();
        text += "\n\t\tMängel: " + maengel.toString();
        return text;
    }
}

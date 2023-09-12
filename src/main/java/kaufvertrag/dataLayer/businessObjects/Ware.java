package kaufvertrag.dataLayer.businessObjects;

import kaufvertrag.businessObjects.IWare;

import java.util.List;

public class Ware implements IWare {

    private long id;
    private String bezeichnung;
    private String beschreibung;
    private double preis;
    private List<String> besonderheiten;
    private List<String> maengel;


    public Ware() {}

    public Ware(String bezeichnung, double preis) {
        this.bezeichnung = bezeichnung;
        this.preis = preis;
    }

    public Ware(long id, String bezeichnung, String beschreibung, double preis, List<String> besonderheiten, List<String> maengel) {
        this.id = id;
        this.bezeichnung = bezeichnung;
        this.beschreibung = beschreibung;
        this.preis = preis;
        this.besonderheiten = besonderheiten;
        this.maengel = maengel;
    }

    public Ware(String bezeichnung, String beschreibung, double preis, List<String> besonderheiten, List<String> maengel) {
        this.bezeichnung = bezeichnung;
        this.beschreibung = beschreibung;
        this.preis = preis;
        this.besonderheiten = besonderheiten;
        this.maengel = maengel;
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

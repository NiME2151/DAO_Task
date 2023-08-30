package kaufvertrag.dataLayer.businessObjects;

import kaufvertrag.businessObjects.IKaufvertrag;
import kaufvertrag.businessObjects.IVertragspartner;
import kaufvertrag.businessObjects.IWare;

public class Kaufvertrag implements IKaufvertrag {

    private Vertragspartner verkaeufer;
    private Vertragspartner kaeufer;
    private Ware ware;
    private String zahlungsModalitaeten;

    public Kaufvertrag(Vertragspartner verkaeufer, Vertragspartner kaeufer, Ware ware) {
        this.verkaeufer = verkaeufer;
        this.kaeufer = kaeufer;
        this.ware = ware;
    }

    @Override
    public Vertragspartner getVerkaeufer() {
        return verkaeufer;
    }

    @Override
    public void setVerkaeufer(Vertragspartner verkaeufer) {
        this.verkaeufer = verkaeufer;
    }

    @Override
    public Vertragspartner getKaeufer() {
        return kaeufer;
    }

    @Override
    public void setKaeufer(Vertragspartner kaeufer) {
        this.kaeufer = kaeufer;
    }

    @Override
    public Ware getWare() {
        return ware;
    }

    @Override
    public void setWare(Ware ware) {
        this.ware = ware;
    }

    @Override
    public String getZahlungsModalitaeten() {
        return zahlungsModalitaeten;
    }

    @Override
    public void setZahlungsModalitaeten(String zahlungsModalitaeten) {
        this.zahlungsModalitaeten = zahlungsModalitaeten;
    }

    @Override
    public String toString() {
        String text = "Kaufvertrag: ";
        text += "\n\tVerkäufer: " + verkaeufer;
        text += "\n\tKäufer: " + kaeufer;
        text += "\n\tWare: " + ware;
        text += "\n\tZahlungsmodalitäten: ";
        text += "\n\t\t" + zahlungsModalitaeten;
        return text;
    }
}

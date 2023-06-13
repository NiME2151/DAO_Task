package entities;

public class Kaufvertrag implements IKaufvertrag {

    private IVertragspartner verkaeufer;
    private IVertragspartner kaeufer;
    private IWare ware;
    private String zahlungsModalitaeten;

    public Kaufvertrag(IVertragspartner verkaeufer, IVertragspartner kaeufer, IWare ware) {
        this.verkaeufer = verkaeufer;
        this.kaeufer = kaeufer;
        this.ware = ware;
    }

    @Override
    public IVertragspartner getVerkaeufer() {
        return verkaeufer;
    }

    @Override
    public void setVerkaeufer(IVertragspartner verkaeufer) {
        this.verkaeufer = verkaeufer;
    }

    @Override
    public IVertragspartner getKaeufer() {
        return kaeufer;
    }

    @Override
    public void setKaeufer(IVertragspartner kaeufer) {
        this.kaeufer = kaeufer;
    }

    @Override
    public IWare getWare() {
        return ware;
    }

    @Override
    public void setWare(IWare ware) {
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
}

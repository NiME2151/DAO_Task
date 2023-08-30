import kaufvertrag.businessObjects.IAdresse;
import kaufvertrag.businessObjects.IVertragspartner;
import kaufvertrag.dataLayer.businessObjects.Adresse;
import kaufvertrag.dataLayer.dataAccessObjects.DataLayerManager;
import kaufvertrag.dataLayer.dataAccessObjects.IDataLayer;
import kaufvertrag.dataLayer.dataAccessObjects.xml.VertragspartnerDaoXml;
import kaufvertrag.exceptions.DaoException;

public class Programm {

    public static void main(String[] args) throws DaoException {

        DataLayerManager dataLayerManager = DataLayerManager.getInstance();
        IDataLayer dataLayer = dataLayerManager.getDataLayer();

        // case xml
    /*    VertragspartnerDaoXml vertragspartnerDaoXml = dataLayer.getDaoVertragspartner();

        IVertragspartner vertragspartner = vertragspartnerDaoXml.create();
        vertragspartner.setVorname("Nico");
        vertragspartner.setNachname("Meyer");
        vertragspartner.setAusweisNr("2911");
        Adresse adresse = new Adresse(
                "Klaus-Groth-Weg",
                "9M",
                "27753",
                "Delmenhorst"
        );
        vertragspartner.setAdresse(adresse);
        vertragspartnerDaoXml.read("2911");
    */}
}
